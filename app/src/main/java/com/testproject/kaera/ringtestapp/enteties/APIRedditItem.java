package com.testproject.kaera.ringtestapp.enteties;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Objects;

public class APIRedditItem {

//    @SerializedName("preview")
//    @Expose
//    private Preview preview;

    @SerializedName("num_comments")
    @Expose
    private int commentsCount;
    @SerializedName("created_utc")
    @Expose
    private long created;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("title")
    @Expose
    private String title;

    private Date createdDate;

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    /**
     * @return crated time Unixtime UTC format
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

    @Override public String toString() {
        return "APIRedditItem{" +
                "numComments=" + commentsCount +
                ", created=" + created +
                ", name='" + name + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        APIRedditItem that = (APIRedditItem) o;
        return Objects.equals(name, that.name);
    }

    @Override public int hashCode() {

        return Objects.hash(name);
    }
}
