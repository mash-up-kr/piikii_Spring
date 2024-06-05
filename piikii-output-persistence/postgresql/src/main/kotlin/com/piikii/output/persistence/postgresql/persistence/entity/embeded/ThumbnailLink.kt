package com.piikii.output.persistence.postgresql.persistence.entity.embeded

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class ThumbnailLink(
    @Column(name = "thumbnail_link")
    var content: String?
) {

    fun saveContentWithIndex(index: Int, url: String) {
        val contentList = getContentList()
        if (index >= 0 && index <= contentList.size) {
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
        if (index >= 0 && index < contentList.size) {
            contentList[index] = url
            content = listToString(contentList)
        } else {
            // TODO 예외 정의
            throw Exception()
        }
    }

    fun deleteWithIndex(index: Int) {
        val contentList = getContentList()
        if (index >= 0 && index < contentList.size) {
            contentList.removeAt(index)
            content = listToString(contentList)
        } else {
            // TODO 예외 정의
            throw Exception()
        }
    }

    private fun getContentList(): MutableList<String> {
        return content?.split(",")?.toMutableList() ?: throw Exception()
    }

    private fun listToString(contents: MutableList<String>): String {
        return contents.joinToString(",")
    }
}
