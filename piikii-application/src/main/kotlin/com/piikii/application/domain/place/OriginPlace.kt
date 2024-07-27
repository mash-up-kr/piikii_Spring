package com.piikii.application.domain.place

import com.piikii.application.domain.generic.Origin
import com.piikii.application.domain.generic.ThumbnailLinks

data class OriginPlace(
    val id: Long?,
    val name: String,
    val originMapId: OriginMapId,
    val url: String,
    val thumbnailLinks: ThumbnailLinks,
    val address: String? = null,
    val phoneNumber: String? = null,
    val starGrade: Float? = null,
    val longitude: Float?,
    val latitude: Float?,
    val reviewCount: Int,
    val category: String?,
    val origin: Origin,
)

@JvmInline
value class OriginMapId(val value: String) {

    fun toId(): String {
        return value.split(SEPARATOR).last()
    }

    companion object {
        private const val SEPARATOR: String = "_"
        fun of(id: Long, origin: Origin): OriginMapId {
            return OriginMapId("${origin.prefix}$SEPARATOR$id")
        }
    }
}
