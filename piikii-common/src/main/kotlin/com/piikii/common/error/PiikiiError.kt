package com.piikii.common.error

class PiikiiError(
    var statusCode: Int,
    var defaultMessage: String,
    var detailMessage: String?,
) : RuntimeException() {

    constructor(errorCode: ErrorCode, detailMessage: String) : this(
        statusCode = errorCode.statusCode,
        defaultMessage = errorCode.defaultMessage,
        detailMessage = detailMessage
    )

    constructor(errorCode: ErrorCode) : this(
        statusCode = errorCode.statusCode,
        defaultMessage = errorCode.defaultMessage,
        detailMessage = null
    )
}

