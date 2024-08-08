package com.piikii.application.domain.generic

import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException

@JvmInline
value class LongTypeId(private val id: Long?) {
    init {
        when {
            id == null -> throw PiikiiException(
                exceptionCode = ExceptionCode.ILLEGAL_ARGUMENT_EXCEPTION,
                detailMessage = "요청 값 `id` 는 Null이 될 수 없습니다.",
            )
            id <= -1 -> throw PiikiiException(
                exceptionCode = ExceptionCode.ILLEGAL_ARGUMENT_EXCEPTION,
                detailMessage = "요청 값 `id` 는 -1보다 커야 합니다. 현재 값: $id",
            )
        }
    }

    fun getValue(): Long = id!!
}
