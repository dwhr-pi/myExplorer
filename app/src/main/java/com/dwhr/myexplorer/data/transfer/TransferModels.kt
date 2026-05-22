package com.dwhr.myexplorer.data.transfer

enum class AccountlessTransferMethod {
    ENCRYPTED_BACKUP_FILE,
    QR_PAIRING,
    NFC_PAIRING,
    BLUETOOTH,
    WIFI_DIRECT,
    LOCAL_NETWORK,
}

data class PairingSession(
    val sessionId: String,
    val oneTimeCode: String,
    val method: AccountlessTransferMethod,
    val expiresAt: String,
)

data class ImportPreview(
    val designSettingsCount: Int,
    val cloudProfileCount: Int,
    val favoriteCount: Int,
    val credentialCount: Int,
    val networkProfileCount: Int,
)
