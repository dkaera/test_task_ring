package com.testproject.kaera.ringtestapp.enteties

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class APIRedditItem {

    @Expose
    @SerializedName("num_comments")
    private var commentsCount: Int = 0

    @SerializedName("created_utc")
    @Expose
    private var created: Long = 0

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("thumbnail")
    @Expose
    private var thumbnail: String? = null

    @SerializedName("author")
    @Expose
    private var author: String? = null

    @SerializedName("title")
    @Expose
    private var title: String? = null

    private var createdDate: Date? = null
    private var thumbFullSize: String? = null

    fun getCommentsCount(): Int {
        return commentsCount
    }

    fun setCommentsCount(commentsCount: Int) {
        this.commentsCount = commentsCount
    }

    /**
     * @return crated time in Unixtime UTC format
     */
    fun getCreated(): Long {
        return created
    }

    fun setCreated(created: Long) {
        this.created = created
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getThumbnail(): String? {
        return thumbnail
    }

    fun setThumbnail(thumbnail: String) {
        this.thumbnail = thumbnail
    }

    fun getAuthor(): String? {
        return author
    }

    fun setAuthor(author: String) {
        this.author = author
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun getCreatedDate(): Date? {
        return createdDate
    }

    fun setCreatedDate(createdDate: Date) {
        this.createdDate = createdDate
    }

    fun getThumbFullSize(): String? {
        return thumbFullSize
    }

    fun setThumbFullSize(thumbFullSize: String) {
        this.thumbFullSize = thumbFullSize
    }

    override fun toString(): String {
        return "APIRedditItem{" +
                "commentsCount=" + commentsCount +
                ", created=" + created +
                ", name='" + name + '\''.toString() +
                ", thumbnail='" + thumbnail + '\''.toString() +
                ", author='" + author + '\''.toString() +
                ", title='" + title + '\''.toString() +
                ", createdDate=" + createdDate +
                ", thumbFullSize='" + thumbFullSize + '\''.toString() +
                '}'.toString()
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as APIRedditItem?
        return commentsCount == that!!.commentsCount &&
                created == that.created &&
                name == that.name &&
                thumbnail == that.thumbnail &&
                author == that.author &&
                title == that.title
    }

    override fun hashCode(): Int {
        return Objects.hash(commentsCount, created, name, thumbnail, author, title)
    }
}