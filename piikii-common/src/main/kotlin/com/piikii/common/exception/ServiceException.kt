package com.piikii.common.exception

class ServiceException : RuntimeException {
    var statusCode: Int
    var defaultMessage: String
    var detailMessage: String? = null

    constructor(
        exceptionCode: ExceptionCode,
        detailMessage: String,
    ) : super("[$exceptionCode] $detailMessage") {
        this.statusCode = exceptionCode.statusCode
        this.defaultMessage = exceptionCode.defaultMessage
        this.detailMessage = detailMessage
    }

    constructor(exceptionCode: ExceptionCode) :
        super("[$exceptionCode] ${exceptionCode.defaultMessage}") {
        this.statusCode = exceptionCode.statusCode
        this.defaultMessage = exceptionCode.defaultMessage
    }
}
