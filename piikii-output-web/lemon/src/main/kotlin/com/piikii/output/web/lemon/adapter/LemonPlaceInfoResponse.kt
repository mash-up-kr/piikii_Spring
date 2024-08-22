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
    val basicInfo: BasicInfo,
    val comment: Comment?,
    val menuInfo: MenuInfo,
    val photo: Photo,
) {
    fun toOriginPlace(url: String): OriginPlace {
        val fullAddress = "${basicInfo.address.region.newaddrfullname} ${basicInfo.address.newaddr.newaddrfull}".trim()
        return OriginPlace(
            id = LongTypeId(0L),
            name = basicInfo.name,
            originMapId = OriginMapId.of(id = LongTypeId(basicInfo.cid), origin = Origin.LEMON),
            url = url,
            thumbnailLinks = ThumbnailLinks(basicInfo.mainPhotoUrl),
            address = fullAddress,
            phoneNumber = basicInfo.phoneNumber,
            starGrade = basicInfo.feedback.calculateStarGrade(),
            longitude = basicInfo.longitude,
            latitude = basicInfo.latitude,
            reviewCount = basicInfo.feedback.countOfBlogReview,
            category = basicInfo.category.firstCategoryName,
            openingHours = basicInfo.openHour.toPrintFormat(),
            origin = Origin.LEMON,
        )
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class BasicInfo(
        val cid: Long,
        @JsonProperty("placenamefull")
        val name: String,
        @JsonProperty("mainphotourl")
        val mainPhotoUrl: String,
        @JsonProperty("phonenum")
        val phoneNumber: String?,
        val address: Address,
        val homepage: String?,
        val category: Category,
        val feedback: Feedback,
        val openHour: OpenHour,
        val tags: List<String>?,
        @JsonProperty("x")
        val longitude: Double?,
        @JsonProperty("y")
        val latitude: Double?,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Address(
        val newaddr: NewAddress,
        val region: Region,
        val addrbunho: String? = null,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class NewAddress(
        val newaddrfull: String,
        val bsizonno: String,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Region(
        val name3: String,
        val fullname: String,
        val newaddrfullname: String,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Category(
        @JsonProperty("catename")
        val firstCategoryName: String,
        @JsonProperty("cate1name")
        val secondCategoryName: String,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Feedback(
        @JsonProperty("scoresum")
        val sumOfScore: Int,
        @JsonProperty("scorecnt")
        val countOfScore: Int,
        @JsonProperty("blogrvwcnt")
        val countOfBlogReview: Int,
        @JsonProperty("comntcnt")
        val countOfReviewComment: Int,
        @JsonProperty("allphotocnt")
        val countOfAllPhoto: Int,
        @JsonProperty("reviewphotocnt")
        val countOfPhotoReview: Int,
    ) {
        fun calculateStarGrade(): Double? = if (countOfScore > 0) sumOfScore.toDouble() / countOfScore else null
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class OpenHour(
        val periodList: List<Period>?,
        val offdayList: List<Offday>?,
    ) {
        fun toPrintFormat(): String? {
            val openingHour =
                periodList?.first { it.periodName == OPEN_HOUR_PERIOD_NAME }
                    ?.toPrintFormat()
            val offdaySchedule =
                offdayList?.map { it.toPrintFormat() }
                    ?.joinToString { JOINER }
            return if (openingHour == null && offdaySchedule == null) null else "$openingHour$JOINER$offdaySchedule"
        }

        companion object {
            const val OPEN_HOUR_PERIOD_NAME = "영업기간"
            const val JOINER = "\n"
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Period(
        val periodName: String,
        val timeList: List<Time>?,
    ) {
        fun toPrintFormat(): String? {
            return timeList?.joinToString(OpenHour.JOINER) { "${it.timeName}: ${it.dayOfWeek} ${it.timeSE}" }
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Time(
        val timeName: String,
        val timeSE: String,
        val dayOfWeek: String,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Offday(
        val holidayName: String,
        val weekAndDay: String,
        val temporaryHolidays: String,
    ) {
        fun toPrintFormat(): String {
            return "$holidayName: $weekAndDay"
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Comment(
        val kamapComntcnt: Int,
        val scoresum: Int,
        val scorecnt: Int,
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
        val desc: String?,
        val img: String?,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Photo(
        val photoCount: Int,
        val photoList: List<PhotoItem>?,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class PhotoItem(
        val photoid: String,
        val orgurl: String,
    )
}
