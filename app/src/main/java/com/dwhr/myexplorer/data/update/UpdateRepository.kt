package com.dwhr.myexplorer.data.update

import com.dwhr.myexplorer.BuildConfig
import com.dwhr.myexplorer.data.config.AppConfig
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class UpdateRepository {
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun check(config: AppConfig): UpdateStatus = withContext(Dispatchers.IO) {
        val manifestUrl = config.updates.manifestUrl ?: return@withContext UpdateStatus.NotConfigured
        runCatching {
            val connection = URL(manifestUrl).openConnection() as HttpURLConnection
            connection.connectTimeout = 8_000
            connection.readTimeout = 8_000
            connection.requestMethod = "GET"
            connection.inputStream.bufferedReader().use { it.readText() }
        }.mapCatching { payload ->
            json.decodeFromString<UpdateManifest>(payload)
        }.fold(
            onSuccess = { manifest ->
                if (manifest.productId != config.productId || manifest.channel != config.updates.channel) {
                    UpdateStatus.Failed("Update-Manifest passt nicht zu Produkt oder Kanal.")
                } else if (manifest.latestVersionCode > BuildConfig.VERSION_CODE) {
                    UpdateStatus.Available(
                        currentVersionName = BuildConfig.VERSION_NAME,
                        latestVersionName = manifest.latestVersionName,
                        downloadUrl = manifest.downloadUrl,
                        releaseNotesUrl = manifest.releaseNotesUrl,
                        required = manifest.required,
                    )
                } else {
                    UpdateStatus.UpToDate(BuildConfig.VERSION_NAME)
                }
            },
            onFailure = { UpdateStatus.Failed(it.message ?: "Updateprüfung fehlgeschlagen.") },
        )
    }
}
