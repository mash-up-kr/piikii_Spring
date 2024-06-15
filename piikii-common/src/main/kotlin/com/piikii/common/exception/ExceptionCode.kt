package com.piikii.common.exception

enum class ExceptionCode(
    val statusCode: Int,
    val defaultMessage: String,
) {
    ILLEGAL_ARGUMENT_EXCEPTION(400, "요청 값이 올바르지 않습니다."),
    UNAUTHORIZED(401, "인증된 토큰으로부터의 요청이 아닙니다."),
    ACCESS_DENIED(403, "해당 리소스에 접근할 수 없습니다."),
    NOT_FOUNDED(404, "해당 리소스를 찾을 수 없습니다."),
    CONFLICT(409, "해당 리소스가 중복됩니다."),
}
