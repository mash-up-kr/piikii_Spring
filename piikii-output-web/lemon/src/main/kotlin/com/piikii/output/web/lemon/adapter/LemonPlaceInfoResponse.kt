package com.piikii.output.web.lemon.adapter

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.piikii.application.domain.generic.Origin
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.place.OriginPlace

@JsonIgnoreProperties(ignoreUnknown = true)
data class LemonPlaceInfoResponse(
    val isMapUser: String?,
    val isExist: Boolean?,
    val basicInfo: BasicInfo,
    val comment: Comment,
    val menuInfo: MenuInfo,
    val photo: Photo,
) {
    fun toOriginPlace(url: String): OriginPlace {
        val fullAddress = "${basicInfo.address.region.newaddrfullname} ${basicInfo.address.newaddr.newaddrfull}".trim()
        return OriginPlace(
            id = null,
            name = basicInfo.name,
            originMapId = basicInfo.cid,
            url = url,
            thumbnailLinks = ThumbnailLinks(basicInfo.mainPhotoUrl),
            address = fullAddress,
            phoneNumber = basicInfo.phoneNumber,
            starGrade = basicInfo.feedback.calculateStarGrade(),
            longitude = basicInfo.longitude,
            latitude = basicInfo.latitude,
            reviewCount = basicInfo.feedback.countOfBlogReview,
            category = basicInfo.category.firstCategoryName,
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
        val phoneNumber: String,
        val address: Address,
        val homepage: String?,
        val category: Category,
        val feedback: Feedback,
        val openHour: OpenHour,
        val tags: List<String>?,
        @JsonProperty("x")
        val longitude: Float?,
        @JsonProperty("y")
        val latitude: Float?,
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
        fun calculateStarGrade(): Float? = if (countOfScore > 0) sumOfScore.toFloat() / countOfScore else null
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class OpenHour(
        val periodList: List<Period>?,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Period(
        val periodName: String,
        val timeList: List<Time>?,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Time(
        val timeName: String,
        val timeSE: String,
        val dayOfWeek: String,
    )

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
