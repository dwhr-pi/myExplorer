package com.dwhr.myexplorer.data.logging

import com.dwhr.myexplorer.data.config.ErrorReportingConfig
import com.dwhr.myexplorer.data.security.CredentialVault

class ErrorReportingCredentials(
    private val vault: CredentialVault,
) {
    fun saveSmtpPassword(config: ErrorReportingConfig, password: String) {
        val reference = requireNotNull(config.smtpPasswordCredentialRef) {
            "SMTP password credential reference is missing."
        }
        vault.saveCredential(reference, password)
    }

    fun loadSmtpPassword(config: ErrorReportingConfig): String? {
        val reference = config.smtpPasswordCredentialRef ?: return null
        return vault.loadCredential(reference)
    }

    fun deleteSmtpPassword(config: ErrorReportingConfig) {
        val reference = config.smtpPasswordCredentialRef ?: return
        vault.deleteCredential(reference)
    }
}
