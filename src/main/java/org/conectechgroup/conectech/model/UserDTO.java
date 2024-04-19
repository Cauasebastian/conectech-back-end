package org.conectechgroup.conectech.model;

import java.util.Date;

public class UserDTO {
    private String id;
    private String name;
    private String email;
    private int Posts;
    private Date birthDate;
    private String gender;

    public UserDTO() {
    }

    public UserDTO(String id, String name, String email, int posts, Date birthDate, String gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        Posts = posts;
        this.birthDate = birthDate;
        this.gender = gender;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPosts() {
        return Posts;
    }

    public void setPosts(int posts) {
        Posts = posts;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
