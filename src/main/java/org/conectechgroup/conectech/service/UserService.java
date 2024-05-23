 package org.conectechgroup.conectech.service;

import com.mongodb.DuplicateKeyException;
import org.conectechgroup.conectech.model.Event;
import org.conectechgroup.conectech.model.Interest;
import org.conectechgroup.conectech.model.Post;
import org.conectechgroup.conectech.DTO.PostDTO;
import org.conectechgroup.conectech.model.User;
import org.conectechgroup.conectech.DTO.UserDTO;
import org.conectechgroup.conectech.repository.PostRepository;
import org.conectechgroup.conectech.repository.UserRepository;
import org.conectechgroup.conectech.exception.ObjectNotFoundException;
import org.conectechgroup.conectech.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
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
    @Autowired
    private UserRepository userRepository;

    /**
     * Fetches all users on the DTO format.
     * @return List of all users.
     */
    public List<UserDTO> findAll() {
        //return users in DTO format
        return repo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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
     * Updates a follower of a user.
     * @param user The user object with updated information.
     * @return The updated user object.
     */
    public User updateFollower(User user) {
        Optional<User> optionalUser = userRepository.findById(user.getId());
        if (optionalUser.isPresent()) {
            User dbUser = optionalUser.get();
            dbUser.setFollowing(user.getFollowing());
            dbUser.setFollowers(user.getFollowers());
            return userRepository.save(dbUser);
        } else {
            throw new RuntimeException("User not found");
        }
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
     * Updates a user profile.
     * @param obj The user object with updated information.
     * @return The updated user object.
     */
    public User updateProfile(User obj) {
        Optional<User> optionalUser = userRepository.findById(obj.getId());
        if (optionalUser.isPresent()) {
            User dbUser = optionalUser.get();
            dbUser.setName(obj.getName());
            dbUser.setEmail(obj.getEmail());
            dbUser.setDateOfBirth(obj.getDateOfBirth());
            dbUser.setGender(obj.getGender());
            dbUser.setPassword(obj.getPassword());
            return userRepository.save(dbUser);
        } else {
            throw new RuntimeException("User not found");
        }
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
        newObj.setGender(obj.getGender());
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
                .map(postService::convertToDTO) // Use PostService to convert Post to PostDTO
                .collect(Collectors.toList());
    }

    /**
     * Deletes a post by id.
     * @param postId The id of the post to be deleted.
     */
    public void deletePost(String postId) {
        postService.delete(Integer.parseInt(postId)); // Parse String to Integer
    }

    /**
     * Converts a User object to UserDTO.
     * @param user The User object.
     * @return The UserDTO object.
     */
    public UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setBirthDate(user.getDateOfBirth());
        dto.setGender(user.getGender());
        dto.setInterests(user.getInterests().stream().map(Interest::getName).collect(Collectors.toList()));
        dto.setFollowers(user.getFollowers().stream().map(User::getName).collect(Collectors.toList()));
        dto.setFollowing(user.getFollowing().stream().map(User::getName).collect(Collectors.toList()));
        dto.setEventsParticipatedIn(user.getEventsParticipatedIn().stream().map(Event::getTitle).collect(Collectors.toList()));
        dto.setPosts(user.getPosts().stream().map(Post::getTitle).collect(Collectors.toList()));
        return dto;
    }

    /**
     * Adds an event to the list of events participated in by a user.
     * @param userId The id of the user.
     * @param event The event to be added.
     * @return The updated user object.
     */
    public User addEventToUser(String userId, Event event) {
        User user = findById(userId); // Retrieve the user by id
        event.getParticipants().add(user); // Add the user to the event's participants
        user.getEventsParticipatedIn().add(event); // Add the event to the user's participated events
        return repo.save(user); // Save the updated user object
    }

    /**
     * Adds an interest to the list of interests of a user.
     * @param userId The id of the user.
     * @param interest The interest to be added.
     * @return The updated user object.
     */
    public User addInterestToUser(String userId, Interest interest) {
        User user = findById(userId);
        user.getInterests().add(interest);
        return repo.save(user);
    }

    /**
     * Removes an interest from the list of interests of a user.
     * @param userId The id of the user.
     * @param interest The interest to be removed.
     * @return The updated user object.
     */
    public User removeInterestFromUser(String userId, Interest interest) {
        User user = findById(userId);
        user.getInterests().remove(interest);
        return repo.save(user);
    }
    /** remove event from user
     * @param userId The id of the user.
     * @param event The event to be removed.
     * @return The updated user object.
     */
    public User removeEventFromUser(String userId, Event event) {
        User user = findById(userId);
        user.getEventsParticipatedIn().remove(event);
        return repo.save(user);
    }
    /**
     * find user by email and password
     * @param email The email of the user.
     * @param password The password of the user.
     * @return The user object.
     */
    public User findByEmailAndPassword(String email, String password) {
        return repo.findByEmailAndPassword(email, password);}

    public User saveUserWithImage(String id, MultipartFile imageFile) throws IOException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setProfileImage(imageFile.getBytes());
            user.setImageContentType(imageFile.getContentType());
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public byte[] getUserImage(String id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return user.getProfileImage();
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
