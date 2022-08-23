package dev.mcullenm.contentmanager

open class CoreException(message: String, cause: Exception?) : Exception(message, cause)

class NullFieldException(field: String, functionName: String) :
    CoreException(message = "$functionName: $field can not be null", null)

class FailureResponse(
    type: String,
    reason: String,
    cause: Exception?
) : CoreException(message = "Failed to perform $type: $reason", cause = cause)
