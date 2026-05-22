package com.dwhr.myexplorer.data.cloud

import com.dwhr.myexplorer.data.model.CloudProfile

interface CloudStorageClient {
    suspend fun testConnection(profile: CloudProfile): ConnectionTestResult
    suspend fun listFolder(profile: CloudProfile, path: String): List<RemoteFileEntry>
    suspend fun upload(profile: CloudProfile, localUri: String, remotePath: String): TransferProgress
    suspend fun download(profile: CloudProfile, remotePath: String, localUri: String): TransferProgress
    suspend fun copy(profile: CloudProfile, sourcePath: String, targetPath: String): OperationResult
    suspend fun move(profile: CloudProfile, sourcePath: String, targetPath: String): OperationResult
    suspend fun delete(profile: CloudProfile, remotePath: String): OperationResult
    suspend fun createFolder(profile: CloudProfile, remotePath: String): OperationResult
}
