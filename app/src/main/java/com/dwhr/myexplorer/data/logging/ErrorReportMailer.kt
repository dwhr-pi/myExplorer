package com.dwhr.myexplorer.data.logging

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.dwhr.myexplorer.data.config.ErrorReportingConfig
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ErrorReportMailer {
    private val json = Json { prettyPrint = true }

    fun createEmailIntent(
        context: Context,
        config: ErrorReportingConfig,
        report: ErrorReportEnvelope,
    ): Intent {
        val subject = "${config.subjectPrefix} Fehlerprotokoll ${report.reportId}"
        val body = buildString {
            appendLine("MyExplorer Fehlerprotokoll")
            appendLine()
            appendLine("Produkt: ${report.productId}")
            appendLine("App-ID: ${report.applicationId}")
            appendLine("Installation: ${report.installId}")
            appendLine("Report: ${report.reportId}")
            appendLine("Erstellt: ${report.generatedAt}")
            appendLine()
            appendLine(json.encodeToString(report))
        }

        return Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:${Uri.encode(config.recipientEmail)}")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(config.recipientEmail))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }.let { Intent.createChooser(it, "Fehlerprotokoll senden") }
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
}
