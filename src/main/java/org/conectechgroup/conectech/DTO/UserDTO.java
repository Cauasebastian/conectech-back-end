package org.conectechgroup.conectech.DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDTO {
    private String id;
    private String name;
    private String email;
    private Date birthDate;
    private String gender;

    private int eventsParticipatedIn;

    private List<String> interests = new ArrayList<>();

    private List<String> posts = new ArrayList<>();

    public UserDTO() {
    }

    public UserDTO(String id, String name, String email, Date birthDate, String gender, int eventsParticipatedIn, List<String> interests, List<String> posts) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.gender = gender;
        this.eventsParticipatedIn = eventsParticipatedIn;
        this.interests = interests;
        this.posts = posts;
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

    public int getEventsParticipatedIn() {
        return eventsParticipatedIn;
    }

    public void setEventsParticipatedIn(int eventsParticipatedIn) {
        this.eventsParticipatedIn = eventsParticipatedIn;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public List<String> getPosts() {
        return posts;
    }

    public void setPosts(List<String> posts) {
        this.posts = posts;
    }
}
