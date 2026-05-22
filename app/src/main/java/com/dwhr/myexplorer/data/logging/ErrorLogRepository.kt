package com.dwhr.myexplorer.data.logging

import android.content.Context
import com.dwhr.myexplorer.BuildConfig
import com.dwhr.myexplorer.data.config.AppConfig
import java.time.Instant
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ErrorLogRepository(
    private val context: Context,
) {
    private val json = Json { ignoreUnknownKeys = true }
    private val logFile get() = context.filesDir.resolve("logs/myexplorer-error-log.jsonl")

    suspend fun append(
        config: AppConfig,
        severity: ErrorSeverity,
        area: String,
        message: String,
        throwable: Throwable? = null,
        metadata: Map<String, String> = emptyMap(),
    ): ErrorLogEntry = withContext(Dispatchers.IO) {
        val entry = ErrorLogEntry(
            id = UUID.randomUUID().toString(),
            productId = config.productId,
            applicationId = config.applicationId,
            installId = installId(),
            appVersionName = BuildConfig.VERSION_NAME,
            appVersionCode = BuildConfig.VERSION_CODE.toLong(),
            channel = config.updates.channel,
            createdAt = Instant.now().toString(),
            severity = severity,
            area = area,
            message = message,
            stackTrace = throwable?.stackTraceToString(),
            context = metadata,
        )
        logFile.parentFile?.mkdirs()
        logFile.appendText(json.encodeToString(entry) + "\n")
        entry
    }

    suspend fun latest(limit: Int = 50): List<ErrorLogEntry> = withContext(Dispatchers.IO) {
        if (!logFile.exists()) return@withContext emptyList()
        logFile.readLines()
            .takeLast(limit)
            .mapNotNull { runCatching { json.decodeFromString<ErrorLogEntry>(it) }.getOrNull() }
    }

    suspend fun buildReport(config: AppConfig, limit: Int = 50): ErrorReportEnvelope = withContext(Dispatchers.IO) {
        ErrorReportEnvelope(
            reportId = "myexplorer-${UUID.randomUUID()}",
            productId = config.productId,
            applicationId = config.applicationId,
            installId = installId(),
            generatedAt = Instant.now().toString(),
            entries = latest(limit),
        )
    }

    private fun installId(): String {
        val prefs = context.getSharedPreferences("myexplorer_installation", Context.MODE_PRIVATE)
        val existing = prefs.getString("install_id", null)
        if (existing != null) return existing
        val created = "myexplorer-${UUID.randomUUID()}"
        prefs.edit().putString("install_id", created).apply()
        return created
    }
}
