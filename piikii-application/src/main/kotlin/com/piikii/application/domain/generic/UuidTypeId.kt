package com.piikii.application.domain.generic

import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import java.util.UUID

@JvmInline
value class UuidTypeId(private val id: UUID?) {
    init {
        if (id == null) {
            throw PiikiiException(
                exceptionCode = ExceptionCode.ILLEGAL_ARGUMENT_EXCEPTION,
                detailMessage = "요청 값 `id` 는 Null이 될 수 없습니다.",
            )
        }
        if (!isValidUUID(id.toString())) {
            throw PiikiiException(
                exceptionCode = ExceptionCode.ILLEGAL_ARGUMENT_EXCEPTION,
                detailMessage = "올바른 UUID 형식이 아닙니다: $id",
            )
        }
    }

    fun getValue(): UUID =
        id ?: throw PiikiiException(
            exceptionCode = ExceptionCode.ILLEGAL_ARGUMENT_EXCEPTION,
            detailMessage = "요청 값 `id` 는 Null이 될 수 없습니다.",
        )

    companion object {
        private val UUID_REGEX =
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$".toRegex()

        private fun isValidUUID(uuid: String): Boolean {
            return UUID_REGEX.matches(uuid)
        }
    }
}
