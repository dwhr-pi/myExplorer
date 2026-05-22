package com.dwhr.myexplorer.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.viewModelScope
import com.dwhr.myexplorer.data.model.AppThemeMode
import com.dwhr.myexplorer.data.provider.CloudProviderRepository
import com.dwhr.myexplorer.data.settings.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyExplorerViewModel(
    application: Application,
) : ViewModel() {
    private val settingsRepository = SettingsRepository(application)
    private val providerRepository = CloudProviderRepository(application)

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
