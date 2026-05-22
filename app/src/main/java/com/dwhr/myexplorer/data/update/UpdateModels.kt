package com.dwhr.myexplorer.data.update

import kotlinx.serialization.Serializable

@Serializable
data class UpdateManifest(
    val productId: String,
    val channel: String,
    val latestVersionCode: Long,
    val latestVersionName: String,
    val downloadUrl: String? = null,
    val releaseNotesUrl: String? = null,
    val publishedAt: String? = null,
    val required: Boolean = false,
)

sealed interface UpdateStatus {
    data object NotConfigured : UpdateStatus
    data object Checking : UpdateStatus
    data class UpToDate(val currentVersionName: String) : UpdateStatus
    data class Available(
        val currentVersionName: String,
        val latestVersionName: String,
        val downloadUrl: String?,
        val releaseNotesUrl: String?,
        val required: Boolean,
    ) : UpdateStatus
    data class Failed(val message: String) : UpdateStatus
}
