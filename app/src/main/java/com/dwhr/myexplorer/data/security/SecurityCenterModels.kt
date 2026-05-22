package com.dwhr.myexplorer.data.security

data class SecurityFinding(
    val id: String,
    val title: String,
    val severity: SecuritySeverity,
    val actionLabel: String? = null,
)

enum class SecuritySeverity {
    INFO,
    WARNING,
    CRITICAL,
}

data class AppLockSettings(
    val appLockEnabled: Boolean = false,
    val biometricUnlockEnabled: Boolean = false,
    val requireUnlockForExport: Boolean = true,
    val requireUnlockForCredentials: Boolean = true,
)
