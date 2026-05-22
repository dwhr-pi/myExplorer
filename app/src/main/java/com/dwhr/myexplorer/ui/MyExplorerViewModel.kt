package com.dwhr.myexplorer.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.viewModelScope
import com.dwhr.myexplorer.data.config.AppConfig
import com.dwhr.myexplorer.data.config.AppConfigRepository
import com.dwhr.myexplorer.data.logging.ErrorReportMailer
import com.dwhr.myexplorer.data.logging.ErrorSeverity
import com.dwhr.myexplorer.data.logging.ErrorLogRepository
import com.dwhr.myexplorer.data.model.AppThemeMode
import com.dwhr.myexplorer.data.provider.CloudProviderRepository
import com.dwhr.myexplorer.data.settings.SettingsRepository
import com.dwhr.myexplorer.data.update.UpdateRepository
import com.dwhr.myexplorer.data.update.UpdateStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyExplorerViewModel(
    private val application: Application,
) : ViewModel() {
    private val settingsRepository = SettingsRepository(application)
    private val providerRepository = CloudProviderRepository(application)
    private val appConfigRepository = AppConfigRepository(application)
    private val errorLogRepository = ErrorLogRepository(application)
    private val errorReportMailer = ErrorReportMailer()
    private val updateRepository = UpdateRepository()

    private val mutableState = MutableStateFlow(MyExplorerState())
    val uiState = mutableState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                settingsRepository.themeMode,
                settingsRepository.dynamicColor,
            ) { themeMode, dynamicColor -> themeMode to dynamicColor }
                .collect { (themeMode, dynamicColor) ->
                    mutableState.update {
                        it.copy(themeMode = themeMode, dynamicColor = dynamicColor)
                    }
                }
        }

        viewModelScope.launch {
            val providers = providerRepository.loadProviders()
            mutableState.update { it.copy(providers = providers) }
        }

        viewModelScope.launch {
            val config = appConfigRepository.loadConfig()
            val errorCount = errorLogRepository.latest(limit = 200).size
            mutableState.update { it.copy(appConfig = config, errorLogCount = errorCount) }
            checkForUpdates(config)
        }
    }

    fun setThemeMode(mode: AppThemeMode) {
        viewModelScope.launch {
            settingsRepository.setThemeMode(mode)
        }
    }

    fun setDynamicColor(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setDynamicColor(enabled)
        }
    }

    fun sendErrorReport() {
        viewModelScope.launch {
            val config = mutableState.value.appConfig ?: appConfigRepository.loadConfig()
            errorLogRepository.append(
                config = config,
                severity = ErrorSeverity.INFO,
                area = "manual_error_report",
                message = "User requested an error report email.",
            )
            val report = errorLogRepository.buildReport(config)
            val intent = errorReportMailer.createEmailIntent(
                context = application,
                config = config.errorReporting,
                report = report,
            )
            application.startActivity(intent)
            mutableState.update { it.copy(errorLogCount = errorLogRepository.latest(limit = 200).size) }
        }
    }

    private suspend fun checkForUpdates(config: AppConfig) {
        mutableState.update { it.copy(updateStatus = UpdateStatus.Checking) }
        val status = updateRepository.check(config)
        mutableState.update { it.copy(updateStatus = status) }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) {
                    "Application must be provided to MyExplorerViewModel"
                }
                MyExplorerViewModel(application)
            }
        }
    }
}
