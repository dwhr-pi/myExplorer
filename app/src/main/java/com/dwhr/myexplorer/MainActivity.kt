package com.dwhr.myexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dwhr.myexplorer.ui.MyExplorerApp
import com.dwhr.myexplorer.ui.MyExplorerViewModel
import com.dwhr.myexplorer.ui.theme.MyExplorerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MyExplorerViewModel = viewModel(factory = MyExplorerViewModel.Factory)
            val state by viewModel.uiState.collectAsState()

            MyExplorerTheme(
                themeMode = state.themeMode,
                dynamicColor = state.dynamicColor,
            ) {
                MyExplorerApp(
                    state = state,
                    onThemeModeChanged = viewModel::setThemeMode,
                    onDynamicColorChanged = viewModel::setDynamicColor,
                    onSendErrorReport = viewModel::sendErrorReport,
                )
            }
        }
    }
}
