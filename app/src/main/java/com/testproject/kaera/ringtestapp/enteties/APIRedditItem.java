package com.testproject.kaera.ringtestapp.enteties;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Objects;

public class APIRedditItem {

    @SerializedName("num_comments")
    @Expose private int commentsCount;

    @SerializedName("created_utc")
    @Expose private long created;

    @SerializedName("name")
    @Expose private String name;

    @SerializedName("thumbnail")
    @Expose private String thumbnail;

    @SerializedName("author")
    @Expose private String author;

    @SerializedName("title")
    @Expose private String title;

    private Date createdDate;
    private String thumbFullSize;

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    /**
     * @return crated time in Unixtime UTC format
     */
    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getThumbFullSize() {
        return thumbFullSize;
    }

    public void setThumbFullSize(String thumbFullSize) {
        this.thumbFullSize = thumbFullSize;
    }

    @Override public String toString() {
        return "APIRedditItem{" +
                "commentsCount=" + commentsCount +
                ", created=" + created +
                ", name='" + name + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", createdDate=" + createdDate +
                ", thumbFullSize='" + thumbFullSize + '\'' +
                '}';
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        APIRedditItem that = (APIRedditItem) o;
        return commentsCount == that.commentsCount &&
                created == that.created &&
                Objects.equals(name, that.name) &&
                Objects.equals(thumbnail, that.thumbnail) &&
                Objects.equals(author, that.author) &&
                Objects.equals(title, that.title);
    }

    @Override public int hashCode() {
        return Objects.hash(commentsCount, created, name, thumbnail, author, title);
    }
}
