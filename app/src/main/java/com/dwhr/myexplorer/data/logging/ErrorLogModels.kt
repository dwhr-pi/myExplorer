package com.dwhr.myexplorer.data.logging

import kotlinx.serialization.Serializable

@Serializable
data class ErrorLogEntry(
    val id: String,
    val productId: String,
    val applicationId: String,
    val installId: String,
    val appVersionName: String,
    val appVersionCode: Long,
    val channel: String,
    val createdAt: String,
    val severity: ErrorSeverity,
    val area: String,
    val message: String,
    val stackTrace: String? = null,
    val context: Map<String, String> = emptyMap(),
)

@Serializable
enum class ErrorSeverity {
    INFO,
    WARNING,
    ERROR,
    FATAL,
}

@Serializable
data class ErrorReportEnvelope(
    val reportId: String,
    val productId: String,
    val applicationId: String,
    val installId: String,
    val generatedAt: String,
    val entries: List<ErrorLogEntry>,
)
