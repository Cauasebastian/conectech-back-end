 package org.conectechgroup.conectech.service;

import com.mongodb.DuplicateKeyException;
import org.conectechgroup.conectech.model.Post;
import org.conectechgroup.conectech.model.PostDTO;
import org.conectechgroup.conectech.model.User;
import org.conectechgroup.conectech.repository.PostRepository;
import org.conectechgroup.conectech.repository.UserRepository;
import org.conectechgroup.conectech.service.exception.ObjectNotFoundException;
import org.conectechgroup.conectech.service.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for User related operations.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    /**
     * Fetches all users.
     * @return List of all users.
     */
    public List<User> findAll() {
        return repo.findAll();
    }

    /**
     * Fetches a user by id.
     * @param id The id of the user.
     * @return The user object.
     * @throws ObjectNotFoundException if the user is not found.
     */
    public User findById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    /**
     * Inserts a new user.
     * @param obj The user object to be inserted.
     * @return The inserted user object.
     * @throws UserAlreadyExistsException if a user with the same id already exists.
     */
    public User insert(User obj) {
        try {
            return repo.insert(obj);
        } catch (DuplicateKeyException e) {
            throw new UserAlreadyExistsException("User already exists with id: " + obj.getId());
        }
    }

    /**
     * Deletes a user by id.
     * @param id The id of the user to be deleted.
     */
    public void delete(String id) {
        findById(id);
        repo.deleteById(id);
    }

    /**
     * Updates a user.
     * @param obj The user object with updated information.
     * @return The updated user object.
     */
    public User update(User obj) {
        User newObj = findById(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    /**
     * Helper method to update user data.
     * @param newObj The user object to be updated.
     * @param obj The user object with new data.
     */
    private void updateData(User newObj, User obj) {
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
        newObj.setDateOfBirth(obj.getDateOfBirth());
        newObj.setCpfcnpj(obj.getCpfcnpj());
        newObj.setPassword(obj.getPassword());
    }

    /**
     * Adds a post to a user.
     * @param userId The id of the user.
     * @param post The post to be added.
     * @return The updated user object.
     */
    public User addPostToUser(String userId, Post post) {
        User user = findById(userId);
        post.setAuthor(user); // Set the author of the post
        user.getPosts().add(post); // Ensure getPosts() returns List<Post>
        return repo.save(user);
    }

    /**
     * Fetches all posts of a user.
     * @param userId The id of the user.
     * @return List of all posts of the user.
     */
    public List<PostDTO> getPosts(String userId) {
        User user = findById(userId);
        return user.getPosts().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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
        dto.setComments(post.getComments());
        dto.setAuthorId(post.getAuthor().getId());
        dto.setAuthorName(post.getAuthor().getName());
        return dto;
    }

    /**
     * Updates the title and description of a post.
     * @param postId The id of the post.
     * @param newTitle The new title.
     * @param newDescription The new description.
     * @return The updated post object.
     */
    public Post updatePostTitleAndDescription(String postId, String newTitle, String newDescription) {
        Post post = postService.findById(postId); // Parse String to Integer
        post.setTitle(newTitle);
        post.setDescription(newDescription);
        return postService.save(post);
    }

    /**
     * Deletes a post by id.
     * @param postId The id of the post to be deleted.
     */
    public void deletePost(String postId) {
        postService.delete(Integer.parseInt(postId)); // Parse String to Integer
    }
}
