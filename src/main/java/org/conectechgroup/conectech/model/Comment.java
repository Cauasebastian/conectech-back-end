package org.conectechgroup.conectech.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "comments")
public class Comment implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    @JsonBackReference(value="author-comment")
    private User author;
    private LocalDateTime date;
    private String Title;

    private String content;
    private int likes;
    @JsonBackReference(value="post-comment") // Se dar error de incompatible media type provavelmente é nesses json reference/managed
    private Post post;

    public Comment(String id, User author, LocalDateTime date, String content, int likes) {
        this.id = id;
        this.author = author;
        this.date = date;
        this.content = content;
        this.likes = likes;
    }

    public Comment() {
    }

    // Getters and setters with validation
    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setAuthor(User author) {
        if (author == null) {
            throw new IllegalArgumentException("Author cannot be null");
        }
        this.author = author;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
        this.content = content;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        if (likes < 0) {
            throw new IllegalArgumentException("Likes cannot be negative");
        }
        this.likes = likes;
    }
    

    // Methods to increment and decrement likes
    public void like() {
        likes++;
    }

    public void unlike() {
        if (likes > 0) {
            likes--;
        }
    }

    public void setPost(Post post) {
        if (post == null) {
            throw new IllegalArgumentException("Post cannot be null");
        }
        this.post = post;
    }

    public User getAuthor() {
        return author;
    }

    public Post getPost() {
        return post;
    }
}