package org.conectechgroup.conectech.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "posts")
public class Post implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    @DBRef(lazy = true)
    @JsonBackReference // Evita recursividade infinita
    private User author;
    private String title;
    private int likes;
    private String description;
    private Date date;

    @DBRef(lazy = true)
    private Forum forum;

    @DBRef(lazy = true)
    private Event event;

    @DBRef(lazy = true)
    private List<Comment> comments = new ArrayList<>();

    @DBRef(lazy = true)
    private List<Interest> tags = new ArrayList<>(); // Tags para facilitar a busca

    public Post() {
    }

    public Post(String id, User author, String title, int likes, String description, Date date, List<Comment> comments, List<Interest> tags) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.likes = likes;
        this.description = description;
        this.date = date;
        this.comments = comments;
        this.tags = tags;
    }

    //#region Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;}

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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Interest> getTags() {
        return tags;
    }
    public void setTags(List<Interest> tags) {
        this.tags = tags;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Forum getForum() {
        return forum;
    }
    public void setForum(Forum forum) {
        this.forum = forum;
    }
    //#endregion
}