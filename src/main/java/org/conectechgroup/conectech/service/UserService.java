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

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;


    public List<User> findAll() {
        return repo.findAll();
    }

    public User findById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public User insert(User obj) {
        try {
            return repo.insert(obj);
        } catch (DuplicateKeyException e) {
            throw new UserAlreadyExistsException("User already exists with id: " + obj.getId());
        }
    }

    public void delete(String id) {
        findById(id);
        repo.deleteById(id);
    }

    public User update(User obj) {
        User newObj = findById(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    private void updateData(User newObj, User obj) {
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
        newObj.setDateOfBirth(obj.getDateOfBirth());
        newObj.setCpfcnpj(obj.getCpfcnpj());
        newObj.setPassword(obj.getPassword());
    }

    public User addPostToUser(String userId, Post post) {
        User user = findById(userId);
        post.setAuthor(user); // Set the author of the post
        user.getPosts().add(post); // Ensure getPosts() returns List<Post>
        return repo.save(user);
    }
    public List<PostDTO> getPosts(String userId) {
        User user = findById(userId);
        return user.getPosts().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

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
    public Post updatePostTitleAndDescription(String postId, String newTitle, String newDescription) {
        Post post = postService.findById(Integer.parseInt(postId)); // Parse String to Integer
        post.setTitle(newTitle);
        post.setDescription(newDescription);
        return postService.save(post);
    }

}