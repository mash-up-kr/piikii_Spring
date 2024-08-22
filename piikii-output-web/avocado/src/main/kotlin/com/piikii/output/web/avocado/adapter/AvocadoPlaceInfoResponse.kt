package com.piikii.output.web.avocado.adapter

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.place.Origin
import com.piikii.application.domain.place.OriginMapId
import com.piikii.application.domain.place.OriginPlace

@JsonIgnoreProperties(ignoreUnknown = true)
data class AvocadoPlaceInfoResponse(
    val id: Long,
    val name: String,
    val visitorReviewScore: Double?,
    val roadAddress: String?,
    val images: List<String>?,
    val imageCount: Int?,
    val visitorReviewCount: Int?,
    val businessType: String?,
    val category: String?,
    val microReview: String?,
    val businessHours: String?,
    val buttons: Buttons,
    @JsonProperty("x")
    val longitude: Double?,
    @JsonProperty("y")
    val latitude: Double?,
) {
    fun toOriginPlace(url: String): OriginPlace {
        return OriginPlace(
            id = LongTypeId(0L),
            originMapId = OriginMapId.of(id = LongTypeId(id), origin = Origin.AVOCADO),
            name = name,
            url = url,
            thumbnailLinks = ThumbnailLinks(images ?: emptyList()),
            address = roadAddress,
            phoneNumber = buttons.phone,
            starGrade = visitorReviewScore,
            longitude = longitude,
            latitude = latitude,
            reviewCount = visitorReviewCount ?: 0,
            category = category,
            openingHours = businessHours,
            origin = Origin.AVOCADO,
        )
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Buttons(
        val phone: String?,
        val route: String?,
        val booking: String?,
    )
}
