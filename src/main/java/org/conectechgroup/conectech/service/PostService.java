package org.conectechgroup.conectech.service;

import org.conectechgroup.conectech.model.Interest;
import org.conectechgroup.conectech.model.Post;
import org.conectechgroup.conectech.DTO.PostDTO;
import org.conectechgroup.conectech.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PostService is a service class that handles business logic related to Post entities.
 */
@Service
public class PostService {

    @Autowired
    private PostRepository repo;

    /**
     * Finds a post by its id.
     *
     * @param id the id of the post
     * @return the post if found, null otherwise
     */
    public Post findById(String id){
        return repo.findById(id).orElse(null);
    }

    /**
     * Finds all posts.
     *
     * @return a list of all posts
     */
    public List<Post> findALL(){
        return repo.findAll();
    }

    /**
     * Finds posts by their title.
     *
     * @param title the title of the posts
     * @return a list of posts with the given title
     */
    public List<Post> findByTitle(String title) {
        return repo.searchTitle(title);
    }

    /**
     * Performs a full search on posts.
     *
     * @param text the text to search for
     * @param minDate the minimum date
     * @param maxDate the maximum date
     * @return a list of posts that match the search criteria
     */
    public List<Post> fullSearch(String text, Date minDate, Date maxDate) {
        maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000);
        return repo.fullSearch(text, minDate, maxDate);
    }

    /**
     * Inserts a new post.
     *
     * @param obj the post to insert
     * @return the inserted post
     */
    public Post insert(Post obj) {
        return repo.insert(obj);
    }

    /**
     * Saves a post.
     *
     * @param post the post to save
     * @return the saved post
     */
    public Post save(Post post) {
        return repo.save(post);
    }
    /**
     * Deletes a post by its id.
     *
     * @param id the id of the post
     */
    public void delete(String id) {
        if (repo.findById (id).isPresent()) {
            repo.deleteById(id);
        }
        else {
            throw new IllegalArgumentException("Post not found");
        }
    }
    /**
     * Updates a post.
     *
     * @param post the post to update
     * @return the updated post
     */
    public Post update(Post post) {
        return repo.save(post);
    }

    /**
     * Converts a Post object to PostDTO.
     * @param post The post object to be converted.
     * @return The converted PostDTO object.
     */
    public PostDTO convertToDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setLikes(post.getLikes());
        dto.setDescription(post.getDescription());
        dto.setDate(post.getDate());
        dto.setCommentsCount(post.getComments().size()); // Set the comments count
        dto.setAuthorId(post.getAuthor().getId());
        dto.setAuthorName(post.getAuthor().getName());
        dto.setTags(post.getTags().stream().map(Interest::getName).collect(Collectors.toList()));
        return dto;
    }
    /** addTagsToPost
     * Adds tags to a post.
     * @param postId The id of the post.
     * @param tags The tags to be added.
     * @return The updated post object.
     */
    public Post addTagsToPost(String postId, List<Interest> tags) {
        Post post = findById(postId);
        post.getTags().addAll(tags);
        return repo.save(post);
    }

}