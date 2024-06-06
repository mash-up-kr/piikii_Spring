package com.piikii.output.persistence.postgresql.persistence.entity.embeded

import com.piikii.output.persistence.postgresql.persistence.entity.consts.THUMBNAIL_LINK_SEPARATOR
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class ThumbnailLink(
    @Column(name = "thumbnail_link")
    var content: String?
) {

    fun saveContentWithIndex(index: Int, url: String) {
        val contentList = getContentList()
        if (hasValidSize(index, contentList)) {
            contentList.add(index, url)
            content = listToString(contentList)
        } else {
            // TODO 예외 정의
            throw Exception()
        }
    }

    fun getContent(): MutableList<String> {
        return getContentList()
    }

    fun updateWithIndex(index: Int, url: String) {
        val contentList = getContentList()
        if (hasValidSize(index, contentList)) {
            contentList[index] = url
            content = listToString(contentList)
        } else {
            // TODO 예외 정의
            throw Exception()
        }
    }

    fun deleteWithIndex(index: Int) {
        val contentList = getContentList()
        if (hasValidSize(index, contentList)) {
            contentList.removeAt(index)
            content = listToString(contentList)
        } else {
            // TODO 예외 정의
            throw Exception()
        }
    }

    private fun getContentList(): MutableList<String> {
        return content?.split(THUMBNAIL_LINK_SEPARATOR)?.toMutableList() ?: throw Exception()
    }

    private fun listToString(contents: List<String>): String {
        return contents.joinToString(THUMBNAIL_LINK_SEPARATOR)
    }

    private fun hasValidSize(index: Int, contents: List<String>) =
        index >= 0 && index <= contents.size
}
