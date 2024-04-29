package org.conectechgroup.conectech.DTO;

import java.util.ArrayList;
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

    private List<String> tags = new ArrayList<>();

    public PostDTO() {
    }

    public PostDTO(String id, String title, int likes, String description, Date date, int commentsCount, String authorId, String authorName, List<String> tags) {
        this.id = id;
        this.title = title;
        this.likes = likes;
        this.description = description;
        this.date = date;
        this.commentsCount = commentsCount;
        this.authorId = authorId;
        this.authorName = authorName;
        this.tags = tags;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
