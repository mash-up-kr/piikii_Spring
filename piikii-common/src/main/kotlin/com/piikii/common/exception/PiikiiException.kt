package com.piikii.common.exception

class PiikiiException(
    var statusCode: Int,
    var defaultMessage: String,
    var detailMessage: String?,
) : RuntimeException() {

    constructor(exceptionCode: ExceptionCode, detailMessage: String) : this(
        statusCode = exceptionCode.statusCode,
        defaultMessage = exceptionCode.defaultMessage,
        detailMessage = detailMessage
    )

    constructor(exceptionCode: ExceptionCode) : this(
        statusCode = exceptionCode.statusCode,
        defaultMessage = exceptionCode.defaultMessage,
        detailMessage = null
    )
}

