package com.dwhr.myexplorer.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CloudProvider(
    val providerId: String,
    val displayName: String,
    val category: String,
    val region: String? = null,
    val freeUsable: String = "unknown",
    val freeStorage: String? = null,
    val webdavSupport: String = "unknown",
    val defaultUrl: String? = null,
    val loginType: List<String> = emptyList(),
    val notes: String = "",
    val securityWarnings: List<String> = emptyList(),
    val status: String = "unknown",
)
