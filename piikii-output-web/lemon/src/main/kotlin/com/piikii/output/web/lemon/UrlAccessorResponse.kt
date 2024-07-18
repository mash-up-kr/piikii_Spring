package com.piikii.output.web.lemon

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.piikii.application.domain.generic.Origin
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.place.OriginPlace

@JsonIgnoreProperties(ignoreUnknown = true)
data class UrlAccessorResponse(
    val isMapUser: String? = null,
    val isExist: Boolean? = null,
    val basicInfo: BasicInfo,
    val comment: Comment,
    val menuInfo: MenuInfo,
    val photo: Photo,
) {
    fun toOriginPlace(url: String): OriginPlace {
        val fullAddress = "${basicInfo.address.region.newaddrfullname} ${basicInfo.address.newaddr.newaddrfull}".trim()
        return OriginPlace(
            originMapId = basicInfo.cid,
            url = url,
            thumbnailLinks = ThumbnailLinks(basicInfo.mainphotourl),
            address = fullAddress,
            phoneNumber = basicInfo.phonenum,
            starGrade = basicInfo.feedback.calculateStarGrade(),
            origin = Origin.LEMON,
        )
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class BasicInfo(
        val cid: Long,
        val placenamefull: String,
        val mainphotourl: String,
        val phonenum: String,
        val address: Address,
        val homepage: String,
        val category: Category,
        val feedback: Feedback,
        val openHour: OpenHour,
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
        val catename: String,
        val cate1name: String,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Feedback(
        val scoresum: Int,
        val scorecnt: Int,
    ) {
        fun calculateStarGrade(): Float? = if (scorecnt > 0) scoresum.toFloat() / scorecnt else null
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class OpenHour(
        val periodList: List<Period>,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Period(
        val periodName: String,
        val timeList: List<Time>,
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
        val list: List<CommentItem>,
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
        val menuList: List<MenuItem>,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class MenuItem(
        val price: String,
        val menu: String,
        val desc: String,
        val img: String,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Photo(
        val photoCount: Int,
        val photoList: List<PhotoItem>,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class PhotoItem(
        val photoid: String,
        val orgurl: String,
    )
}
