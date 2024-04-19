package org.conectechgroup.conectech.model;

import javax.xml.stream.events.Comment;
import java.util.Date;
import java.util.List;

public class PostDTO {
    private String id;
    private String title;
    private int likes;
    private String description;
    private Date date;
    private int commentsCount;
    private String authorId;
    private String authorName;

    public PostDTO() {
    }

    public PostDTO(String id, String title, int likes, String description, Date date, int commentsCount, String authorId, String authorName) {
        this.id = id;
        this.title = title;
        this.likes = likes;
        this.description = description;
        this.date = date;
        this.commentsCount = commentsCount;
        this.authorId = authorId;
        this.authorName = authorName;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setComments(List<org.conectechgroup.conectech.model.Comment> comments) {
    }
}
