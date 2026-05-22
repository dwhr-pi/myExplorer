package com.dwhr.myexplorer.data.provider

import android.content.Context
import com.dwhr.myexplorer.data.model.CloudProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class CloudProviderRepository(
    private val context: Context,
) {
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun loadProviders(): List<CloudProvider> = withContext(Dispatchers.IO) {
        val payload = context.assets.open("cloud_providers.json").bufferedReader().use { it.readText() }
        json.decodeFromString<List<CloudProvider>>(payload)
    }
}
