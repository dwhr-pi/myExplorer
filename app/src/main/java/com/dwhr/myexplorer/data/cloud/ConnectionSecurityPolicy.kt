package com.dwhr.myexplorer.data.cloud

import com.dwhr.myexplorer.data.model.CloudProfile
import java.net.URI

class ConnectionSecurityPolicy {
    fun inspect(profile: CloudProfile): List<String> {
        val uri = runCatching { URI(profile.serverUrl) }.getOrNull()
            ?: return listOf("Server-URL ist ungültig.")

        val scheme = uri.scheme.orEmpty()

        return buildList {
            if (scheme.equals("http", ignoreCase = true)) {
                add("HTTP ist unverschlüsselt und sollte blockiert oder ausdrücklich bestätigt werden.")
            }
            if (!scheme.equals("https", ignoreCase = true) &&
                !scheme.equals("sftp", ignoreCase = true) &&
                !scheme.equals("ftps", ignoreCase = true) &&
                !scheme.equals("smb", ignoreCase = true)
            ) {
                add("Verbindungstyp benötigt eine manuelle Sicherheitsprüfung.")
            }
            if (profile.credentialRef.isNullOrBlank()) {
                add("Profil hat noch keine verschlüsselte Credential-Referenz.")
            }
        }
    }
}
