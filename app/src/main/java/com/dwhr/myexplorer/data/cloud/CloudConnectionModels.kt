package com.dwhr.myexplorer.data.cloud

data class ConnectionTestResult(
    val successful: Boolean,
    val message: String,
    val certificateInfo: CertificateInfo? = null,
    val warnings: List<String> = emptyList(),
)

data class CertificateInfo(
    val subject: String,
    val issuer: String,
    val validFrom: String,
    val validUntil: String,
    val sha256Fingerprint: String,
)

data class RemoteFileEntry(
    val name: String,
    val path: String,
    val type: RemoteFileType,
    val sizeBytes: Long? = null,
    val modifiedAt: String? = null,
)

enum class RemoteFileType {
    FILE,
    FOLDER,
}

data class TransferProgress(
    val completedBytes: Long,
    val totalBytes: Long?,
    val state: TransferState,
)

enum class TransferState {
    QUEUED,
    RUNNING,
    COMPLETED,
    FAILED,
    CANCELED,
}

data class OperationResult(
    val successful: Boolean,
    val message: String,
)
