package org.conectechgroup.conectech.DTO;

import org.conectechgroup.conectech.model.Forum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ForumDTO {

    private String id;
    private String title;
    private String description;
    private Date CreationDate;

    private String authorName;

    private List<String> posts = new ArrayList<>();

    private List<String> moderators = new ArrayList<>();

    private List<String> participants = new ArrayList<>();

    private List<String> events = new ArrayList<>();

    public ForumDTO() {
    }

    public ForumDTO(String id,String title, String description, Date creationDate, String authorName, List<String> posts, List<String> moderators, List<String> participants, List<String> events) {
       this.id = id;
        this.title = title;
        this.description = description;
        this.CreationDate = creationDate;
        this.authorName = authorName;
        this.posts = posts;
        this.moderators = moderators;
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

    public Date getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.CreationDate = creationDate;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public List<String> getPosts() {
        return posts;
    }

    public void setPosts(List<String> posts) {
        this.posts = posts;
    }

    public List<String> getModerators() {
        return moderators;
    }

    public void setModerators(List<String> moderators) {
        this.moderators = moderators;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }

}
