package com.dwhr.myexplorer.data.security

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class CredentialVault(
    context: Context,
) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        "secure_credentials",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
    )

    fun saveCredential(reference: String, secret: String) {
        prefs.edit().putString(reference, secret).apply()
    }

    fun loadCredential(reference: String): String? = prefs.getString(reference, null)

    fun deleteCredential(reference: String) {
        prefs.edit().remove(reference).apply()
    }
}
