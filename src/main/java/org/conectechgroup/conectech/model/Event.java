package org.conectechgroup.conectech.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Document(collection = "events")
public class Event implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private String title;
    private String description;
    private String location;
    private Date date;

    @DBRef
    private Image image;

    @DBRef(lazy = true)
    @JsonBackReference
    private User author;

    @DBRef(lazy = true)
    private List<User> participants = new ArrayList<>();

    @DBRef(lazy = true)
    private List<Post> posts = new ArrayList<>(); // No need for @JsonManagedReference here

    @DBRef(lazy = true)
    private Forum forum;

    public Event() {
    }

    public Event(String id, String title, String description, String location, Date date, User author, List<User> participants, List<Post> posts, Image image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.date = date;
        this.author = author;
        this.participants = participants;
        this.posts = posts;
        this.image = image;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;
        return Objects.equals(getId(), event.getId()) && Objects.equals(getTitle(), event.getTitle()) && Objects.equals(getDescription(), event.getDescription()) && Objects.equals(getLocation(), event.getLocation()) && Objects.equals(getDate(), event.getDate()) && Objects.equals(getImage(), event.getImage()) && Objects.equals(getAuthor(), event.getAuthor()) && Objects.equals(getParticipants(), event.getParticipants()) && Objects.equals(getPosts(), event.getPosts()) && Objects.equals(getForum(), event.getForum());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getId());
        result = 31 * result + Objects.hashCode(getTitle());
        result = 31 * result + Objects.hashCode(getDescription());
        result = 31 * result + Objects.hashCode(getLocation());
        result = 31 * result + Objects.hashCode(getDate());
        result = 31 * result + Objects.hashCode(getImage());
        result = 31 * result + Objects.hashCode(getAuthor());
        result = 31 * result + Objects.hashCode(getParticipants());
        result = 31 * result + Objects.hashCode(getPosts());
        result = 31 * result + Objects.hashCode(getForum());
        return result;
    }
}
