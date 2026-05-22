package com.dwhr.myexplorer.ui

import com.dwhr.myexplorer.data.model.AppThemeMode
import com.dwhr.myexplorer.data.model.CloudProfile
import com.dwhr.myexplorer.data.model.CloudProvider

data class MyExplorerState(
    val themeMode: AppThemeMode = AppThemeMode.DARK,
    val dynamicColor: Boolean = false,
    val providers: List<CloudProvider> = emptyList(),
    val profiles: List<CloudProfile> = sampleProfiles,
) {
    companion object {
        private val sampleProfiles = listOf(
            CloudProfile(
                id = "nextcloud_manual_001",
                type = "webdav",
                displayName = "Meine Nextcloud",
                serverUrl = "https://example.com/remote.php/dav/files/username/",
                username = "username",
                credentialRef = "android_keystore_reference",
                createdAt = "2026-05-21T00:00:00Z",
                updatedAt = "2026-05-21T00:00:00Z",
            )
        )
    }
}
