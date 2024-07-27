package com.piikii.output.web.avocado.adapter

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.piikii.application.domain.generic.Origin
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.place.OriginMapId
import com.piikii.application.domain.place.OriginPlace

@JsonIgnoreProperties(ignoreUnknown = true)
data class AvocadoPlaceInfoResponse(
    val id: Long,
    val name: String,
    val visitorReviewScore: Float?,
    val roadAddress: String?,
    val images: List<String>?,
    val imageCount: Int?,
    val visitorReviewCount: Int?,
    val businessType: String?,
    val category: String?,
    val microReview: String?,
    val buttons: Buttons,
    @JsonProperty("x")
    val longitude: Float?,
    @JsonProperty("y")
    val latitude: Float?,
) {
    fun toOriginPlace(url: String): OriginPlace {
        return OriginPlace(
            id = null,
            originMapId = OriginMapId.of(id = id, origin = Origin.AVOCADO),
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
