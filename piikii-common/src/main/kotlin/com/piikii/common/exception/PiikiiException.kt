package com.piikii.common.exception

class PiikiiException(
    val statusCode: Int,
    override val message: String
) : RuntimeException(message) {

    companion object {
        fun illegalArgumentException(fieldName: String, fieldValue: Any): PiikiiException {
            throw PiikiiException(
                statusCode = 400,
                message = String.format(ILLEGAL_ARGUMENT_EXCEPTION, fieldName, fieldValue)
            )
        }

        fun unauthorizedException(): PiikiiException {
            throw PiikiiException(
                statusCode = 401,
                message = UNAUTHORIZED
            )
        }

        fun accessDeniedException(fieldName: String): PiikiiException {
            throw PiikiiException(
                statusCode = 403,
                message = String.format(ACCESS_DENIED, fieldName)
            )
        }

        fun notFoundedException(fieldName: String, fieldValue: Any): PiikiiException {
            throw PiikiiException(
                statusCode = 404,
                message = String.format(NOT_FOUNDED, fieldName, fieldValue)
            )
        }

        fun conflictException(fieldName: String, fieldValue: Any): PiikiiException {
            throw PiikiiException(
                statusCode = 409,
                message = String.format(CONFLICT, fieldName, fieldValue)
            )
        }
    }
}

const val ILLEGAL_ARGUMENT_EXCEPTION = "[%s] : %s가 올바르지 않습니다."
const val UNAUTHORIZED = "인증된 토큰으로부터의 요청이 아닙니다."
const val ACCESS_DENIED = "해당 [%s]에 접근할 수 없습니다."
const val NOT_FOUNDED = "[%s] : %s 에 해당하는 리소스를 찾을 수 없습니다."
const val CONFLICT = "[%s] : %s가 중복됩니다."
