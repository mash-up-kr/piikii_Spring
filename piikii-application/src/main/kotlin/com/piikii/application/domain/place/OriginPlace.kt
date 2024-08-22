package com.piikii.application.domain.place

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.ThumbnailLinks

data class OriginPlace(
    val id: LongTypeId,
    val name: String,
    val originMapId: OriginMapId,
    val url: String,
    val thumbnailLinks: ThumbnailLinks,
    val address: String? = null,
    val phoneNumber: String? = null,
    val starGrade: Double? = null,
    val longitude: Double?,
    val latitude: Double?,
    val reviewCount: Int,
    val category: String?,
    val openingHours: String?,
    val origin: Origin,
)

@JvmInline
value class OriginMapId(val value: String) {
    fun toId(): String {
        return value.split(SEPARATOR).last()
    }

    companion object {
        private const val SEPARATOR: String = "_"

        fun of(
            id: LongTypeId,
            origin: Origin,
        ): OriginMapId {
            return OriginMapId("${origin.prefix}$SEPARATOR${id.getValue()}")
        }
    }
}
