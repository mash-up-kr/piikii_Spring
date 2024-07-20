package com.piikii.common.exception

enum class ExceptionCode(
    val statusCode: Int,
    val defaultMessage: String,
) {
    // 400
    ILLEGAL_ARGUMENT_EXCEPTION(400, "요청 값이 올바르지 않습니다."),
    VOTE_PLACE_ID_INVALID(400, "투표 항목 데이터(Place Id)이 올바르지 않습니다."),

    UNAUTHORIZED(401, "인증된 토큰으로부터의 요청이 아닙니다."),
    ROOM_PASSWORD_INVALID(401, "방 패스워드가 틀립니다."),
    ACCESS_DENIED(403, "해당 리소스에 접근할 수 없습니다."),
    NOT_FOUNDED(404, "해당 리소스를 찾을 수 없습니다."),
    CONFLICT(409, "해당 리소스가 중복됩니다."),

    // 500
    SECRET_MANAGER_CONFIG_NOT_SET(500, "시크릿 매니저 설정 값이 입력되지 않았습니다."),
    URL_PROCESS_ERROR(500, "URL에 해당하는 장소의 정보를 불러오는 중 예기치 못한 오류가 발생했습니다."),
}
