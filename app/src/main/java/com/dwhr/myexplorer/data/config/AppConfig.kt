package com.dwhr.myexplorer.data.config

import kotlinx.serialization.Serializable

@Serializable
data class AppConfig(
    val productId: String,
    val applicationId: String,
    val displayName: String,
    val errorReporting: ErrorReportingConfig,
    val updates: UpdateConfig,
)

@Serializable
data class ErrorReportingConfig(
    val enabled: Boolean,
    val recipientEmail: String,
    val subjectPrefix: String,
    val smtpUsername: String? = null,
    val smtpPasswordCredentialRef: String? = null,
    val automaticSmtpSendingEnabled: Boolean = false,
)

@Serializable
data class UpdateConfig(
    val channel: String,
    val manifestUrl: String? = null,
    val allowNonPlayStoreUpdates: Boolean = true,
)
