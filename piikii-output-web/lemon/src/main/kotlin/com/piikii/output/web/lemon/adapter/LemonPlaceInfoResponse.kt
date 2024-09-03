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
    @JsonProperty("ismapuser") val isMapUser: String?,
    @JsonProperty("isexist") val isExist: Boolean?,
    @JsonProperty("basicinfo") val basicInfo: BasicInfo?,
    val comment: Comment?,
    @JsonProperty("menuinfo") val menuInfo: MenuInfo?,
    val photo: Photo?,
) {
    fun toOriginPlace(url: String): OriginPlace {
        requireNotNull(basicInfo) { "BasicInfo is required" }
        val fullAddress = "${basicInfo.address.region.newAddrFullName} ${basicInfo.address.newAddr.newAddrFull}".trim()
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
        @JsonProperty("placenamefull") val name: String,
        @JsonProperty("mainphotourl") val mainPhotoUrl: String,
        @JsonProperty("phonenum") val phoneNumber: String?,
        val address: Address,
        val homepage: String?,
        val category: Category,
        val feedback: Feedback,
        @JsonProperty("openhour") val openHour: OpenHour,
        val tags: List<String>?,
        @JsonProperty("x") val longitude: Double?,
        @JsonProperty("y") val latitude: Double?,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Address(
        @JsonProperty("newaddr") val newAddr: NewAddress,
        val region: Region,
        @JsonProperty("addrbunho") val addrBunho: String?,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class NewAddress(
        @JsonProperty("newaddrfull") val newAddrFull: String,
        @JsonProperty("bsizonno") val bsiZonNo: String,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Region(
        val name3: String,
        @JsonProperty("fullname") val fullName: String,
        @JsonProperty("newaddrfullname") val newAddrFullName: String,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Category(
        @JsonProperty("catename") val firstCategoryName: String,
        @JsonProperty("cate1name") val secondCategoryName: String,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Feedback(
        @JsonProperty("scoresum") val sumOfScore: Int = 0,
        @JsonProperty("scorecnt") val countOfScore: Int = 0,
        @JsonProperty("blogrvwcnt") val countOfBlogReview: Int = 0,
        @JsonProperty("comntcnt") val countOfReviewComment: Int = 0,
        @JsonProperty("allphotocnt") val countOfAllPhoto: Int = 0,
        @JsonProperty("reviewphotocnt") val countOfPhotoReview: Int = 0,
    ) {
        fun calculateStarGrade(): Double? = if (countOfScore > 0) sumOfScore.toDouble() / countOfScore else null
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class OpenHour(
        @JsonProperty("periodlist") val periodList: List<Period>?,
        @JsonProperty("offdaylist") val offDayList: List<OffDay>?,
    ) {
        fun toPrintFormat(): String? {
            val openingHour = periodList?.firstOrNull { it.periodName == OPEN_HOUR_PERIOD_NAME }?.toPrintFormat()
            val offDaySchedule = offDayList?.mapNotNull { it.toPrintFormat() }?.joinToString(JOINER)
            return if (openingHour == null && offDaySchedule.isNullOrEmpty()) {
                null
            } else {
                "$openingHour$JOINER$offDaySchedule".trim()
            }
        }

        companion object {
            const val OPEN_HOUR_PERIOD_NAME = "영업기간"
            const val JOINER = "\n"
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Period(
        @JsonProperty("periodname") val periodName: String,
        @JsonProperty("timelist") val timeList: List<Time>?,
    ) {
        fun toPrintFormat(): String? =
            timeList?.joinToString(OpenHour.JOINER) {
                "${it.timeName}: ${it.dayOfWeek} ${it.timeSE}"
            }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Time(
        @JsonProperty("timename") val timeName: String,
        @JsonProperty("timese") val timeSE: String,
        @JsonProperty("dayofweek") val dayOfWeek: String,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class OffDay(
        @JsonProperty("holidayname") val holidayName: String,
        @JsonProperty("weekandday") val weekAndDay: String,
        @JsonProperty("temporaryholidays") val temporaryHolidays: String?,
    ) {
        fun toPrintFormat(): String? =
            if (holidayName.isNotBlank() && weekAndDay.isNotBlank()) {
                "$holidayName: $weekAndDay"
            } else {
                null
            }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Comment(
        @JsonProperty("kamapcomntcnt") val kamapComntCnt: Int = 0,
        @JsonProperty("scoresum") val scoreSum: Int = 0,
        @JsonProperty("scorecnt") val scoreCnt: Int = 0,
        val list: List<CommentItem>?,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class CommentItem(
        val contents: String,
        val point: Int,
        @JsonProperty("username") val userName: String,
        val date: String,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class MenuInfo(
        @JsonProperty("menulist") val menuList: List<MenuItem>?,
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
        @JsonProperty("photocount") val photoCount: Int = 0,
        @JsonProperty("photolist") val photoList: List<PhotoItem>?,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class PhotoItem(
        @JsonProperty("photoid") val photoId: String,
        @JsonProperty("orgurl") val orgUrl: String,
    )
}
