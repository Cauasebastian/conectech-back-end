package org.conectechgroup.conectech.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Document(collection = "users")
public class User implements java.io.Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String name;
    private String username;
    private String email;
    private Date dateOfBirth;
    private String Cpfcnpj;
    private String password;
    private String gender;
    private String bio;

    @DBRef
    private Image profileImage;

    @DBRef
    @JsonIgnoreProperties("user")
    private List<Post> posts = new ArrayList<>();

    @DBRef
    @JsonIgnoreProperties("participants")
    private List<Event> eventsParticipatedIn = new ArrayList<>();

    @DBRef
    @JsonIgnoreProperties("users")
    private List<Forum> forums = new ArrayList<>();

    @DBRef
    private List<Interest> interests = new ArrayList<>();

    @DBRef
    @JsonIgnoreProperties("following")
    private List<User> followers = new ArrayList<>();

    @DBRef
    @JsonIgnoreProperties("followers")
    private List<User> following = new ArrayList<>();

    public User() {
    }

    public User(String id, String name, String email, Date dateOfBirth, String cpfcnpj, String password, String gender, String bio,String username) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.Cpfcnpj = cpfcnpj;
        this.password = password;
        this.gender = gender;
        this.bio = bio;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCpfcnpj() {
        return Cpfcnpj;
    }

    public void setCpfcnpj(String cpfcnpj) {
        Cpfcnpj = cpfcnpj;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Event> getEventsParticipatedIn() {
        return eventsParticipatedIn;
    }
    public void setEventsParticipatedIn(List<Event> eventsParticipatedIn) {
        this.eventsParticipatedIn = eventsParticipatedIn;
    }

    public List<Interest> getInterests() {
        return interests;
    }

    public void setInterests(List<Interest> interests) {
        this.interests = interests;
    }

    public String getGender() {
        return gender;
    }


    public void setGender(String gender) {
        this.gender = gender;
    }

    public User orElseThrow(Object userNotFound) {
        return null;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public Image getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Image profileImage) {
        this.profileImage = profileImage;
    }

    public List<Forum> getForums() {
        return forums;
    }
    public void setForums(List<Forum> forums) {
        this.forums = forums;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
