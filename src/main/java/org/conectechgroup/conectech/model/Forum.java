package org.conectechgroup.conectech.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Document(collection = "forums")
public class Forum implements java.io.Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String title;
    private String description;
    private Date date;

    @DBRef(lazy = true)
    @JsonBackReference
    private User author;

    @DBRef(lazy = true)
    private List<User> moderators = new ArrayList<>();

    @DBRef(lazy = true)
    private List<Post> posts = new ArrayList<>();

    @DBRef(lazy = true)
    private List<User> participants = new ArrayList<>();

    @DBRef(lazy = true)
    private List<Event> events = new ArrayList<>();

    public Forum() {
    }
    public Forum(String id, String title, String description, User author, List<User> moderators, List<Post> posts, List<User> participants, List<Event> events) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.moderators = moderators;
        this.posts = posts;
        this.participants = participants;
        this.events = events;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<User> getModerators() {
        return moderators;
    }

    public void setModerators(List<User> moderators) {
        this.moderators = moderators;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    // Na classe Forum
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Forum forum = (Forum) o;
        return Objects.equals(id, forum.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
