package com.dwhr.myexplorer.data.security

import com.dwhr.myexplorer.data.model.MyExplorerBackupPayload
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class BackupRepository {
    private val json = Json { prettyPrint = true }

    fun exportEncrypted(payload: MyExplorerBackupPayload, password: CharArray): String {
        val raw = json.encodeToString(payload).encodeToByteArray()
        return BackupCrypto.encrypt(raw, password)
    }

    fun importEncrypted(encodedPayload: String, password: CharArray): MyExplorerBackupPayload {
        val raw = BackupCrypto.decrypt(encodedPayload, password)
        return json.decodeFromString(raw.decodeToString())
    }
}
