package org.conectechgroup.conectech.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.stream.events.Comment;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "posts")
public class Post {
    @Id
    private Integer id;
    private String title;
    private Blob image;
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

}
