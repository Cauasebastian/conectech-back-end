package org.conectechgroup.conectech.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
@org.springframework.data.mongodb.core.mapping.Document(collection = "interests")
public class Interest implements java.io.Serializable{
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;

    @JsonBackReference
    private List<Post> posts = new ArrayList<>();

    public Interest() {
    }

    public Interest(String id, String name) {
        this.id = id;
        this.name = name;
    }

    //#region Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
    //#endregion

}