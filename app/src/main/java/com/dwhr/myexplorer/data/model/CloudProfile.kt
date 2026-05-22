package com.dwhr.myexplorer.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CloudProfile(
    val id: String,
    val type: String,
    val displayName: String,
    val serverUrl: String,
    val username: String? = null,
    val credentialRef: String? = null,
    val favoriteFolders: List<String> = emptyList(),
    val syncEnabled: Boolean = false,
    val createdAt: String,
    val updatedAt: String,
)
