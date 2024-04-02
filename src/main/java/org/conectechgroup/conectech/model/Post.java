package org.conectechgroup.conectech.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.stream.events.Comment;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "posts")
public class Post implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    private String title;
    private Blob image;
    private int likes;
    private int ncomments;
    private String description;
    private Date date;

    private List<Comment> comments = new ArrayList<>();

    public Post() {
    }

    public Post(Integer id, String title, Blob image, String description, Date date) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.description = description;
        this.date = date;
    }
    //#region Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getNcomments() {
        return ncomments;
    }

    public void setNcomments(int ncomments) {
        this.ncomments = ncomments;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    //#endregion
}