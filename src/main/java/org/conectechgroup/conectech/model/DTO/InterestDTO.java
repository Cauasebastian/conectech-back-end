package org.conectechgroup.conectech.model.DTO;

public class InterestDTO {
    private String name;
    private int postsCount;

    public InterestDTO() {
    }

    public InterestDTO(String name, int postsCount) {
        this.name = name;
        this.postsCount = postsCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(int postsCount) {
        this.postsCount = postsCount;
    }
}
