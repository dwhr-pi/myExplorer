package com.dwhr.myexplorer.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AppSettingsExport(
    val languageTag: String = "de-DE",
    val sortMode: String = "name",
    val themeMode: AppThemeMode = AppThemeMode.DARK,
    val dynamicColor: Boolean = false,
    val favorites: List<String> = emptyList(),
    val exclusionRules: List<String> = emptyList(),
)

@Serializable
data class DeviceTransferSelection(
    val includeTheme: Boolean = true,
    val includeCloudProfiles: Boolean = true,
    val includeFavorites: Boolean = true,
    val includeCredentials: Boolean = false,
    val includeNetworkSettings: Boolean = true,
)

@Serializable
data class MyExplorerBackupPayload(
    val version: Int = 1,
    val exportedAt: String,
    val settings: AppSettingsExport,
    val profiles: List<CloudProfile>,
    val transportHints: List<String> = emptyList(),
)
