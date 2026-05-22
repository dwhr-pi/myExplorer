package com.dwhr.myexplorer.data.config

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class AppConfigRepository(
    private val context: Context,
) {
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun loadConfig(): AppConfig = withContext(Dispatchers.IO) {
        val payload = context.assets.open("app_config.json").bufferedReader().use { it.readText() }
        json.decodeFromString<AppConfig>(payload)
    }
}
