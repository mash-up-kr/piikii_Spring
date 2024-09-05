package com.piikii.output.web.lemon.adapter

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.place.Origin
import com.piikii.application.domain.place.OriginMapId
import com.piikii.application.domain.place.OriginPlace

@JsonIgnoreProperties(ignoreUnknown = true)
data class LemonPlaceInfoResponse(
    val isMapUser: String?,
    val isExist: Boolean?,
    val basicInfo: BasicInfo?,
    val comment: Comment?,
    val menuInfo: MenuInfo?,
    val photo: Photo?,
) {
    fun toOriginPlace(url: String): OriginPlace {
        requireNotNull(basicInfo) { "BasicInfo is required" }
        val fullAddress =
            "${basicInfo.address.region.fullname ?: ""} ${basicInfo.address.newaddr?.newaddrfull ?: ""}"
                .trim()
        return OriginPlace(
            id = LongTypeId(0L),
            name = basicInfo.placenamefull,
            originMapId = OriginMapId.of(id = LongTypeId(basicInfo.cid), origin = Origin.LEMON),
            url = url,
            thumbnailLinks = ThumbnailLinks(basicInfo.mainphotourl),
            address = fullAddress,
            phoneNumber = null,
            starGrade = basicInfo.feedback.calculateStarGrade(),
            longitude = basicInfo.wpointx?.toDouble(),
            latitude = basicInfo.wpointy?.toDouble(),
            reviewCount = basicInfo.feedback.blogrvwcnt,
            category = basicInfo.category.cate1name,
            openingHours = basicInfo.openHour.toPrintFormat(),
            origin = Origin.LEMON,
        )
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class BasicInfo(
        val cid: Long,
        val placenamefull: String,
        val mainphotourl: String,
        val address: Address,
        val homepage: String?,
        val wpointx: Int?,
        val wpointy: Int?,
        val category: Category,
        val feedback: Feedback,
        val openHour: OpenHour,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Address(
        val newaddr: NewAddress?,
        val region: Region,
        val addrbunho: String?,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class NewAddress(
        val newaddrfull: String?,
        val bsizonno: String?,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Region(
        val name3: String?,
        val fullname: String?,
        val newaddrfullname: String?,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Category(
        val catename: String,
        val cate1name: String,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Feedback(
        val scoresum: Int = 0,
        val scorecnt: Int = 0,
        val blogrvwcnt: Int = 0,
        val comntcnt: Int = 0,
        val allphotocnt: Int = 0,
    ) {
        fun calculateStarGrade(): Double? = if (scorecnt > 0) scoresum.toDouble() / scorecnt else null
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class OpenHour(
        val periodList: List<Period>?,
    ) {
        fun toPrintFormat(): String? {
            return periodList?.firstOrNull()?.toPrintFormat()
        }

        companion object {
            const val JOINER = "\n"
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Period(
        val periodName: String,
        val timeList: List<Time>?,
    ) {
        fun toPrintFormat(): String? =
            timeList?.joinToString(OpenHour.JOINER) {
                "${it.timeName}: ${it.dayOfWeek} ${it.timeSE}"
            }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Time(
        val timeName: String,
        val timeSE: String,
        val dayOfWeek: String,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Comment(
        @JsonProperty("kamapComntcnt") val kamapComntcnt: Int = 0,
        val scoresum: Int = 0,
        val scorecnt: Int = 0,
        val list: List<CommentItem>?,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class CommentItem(
        val contents: String,
        val point: Int,
        val username: String,
        val date: String,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class MenuInfo(
        val menuList: List<MenuItem>?,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class MenuItem(
        val price: String?,
        val menu: String?,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Photo(
        val photoList: List<PhotoCategory>?,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class PhotoCategory(
        val photoCount: Int = 0,
        val categoryName: String?,
        val list: List<PhotoItem>?,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class PhotoItem(
        val photoid: String,
        val orgurl: String,
    )
}
