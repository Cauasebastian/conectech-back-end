package org.conectechgroup.conectech.controller;

import org.conectechgroup.conectech.DTO.ForumDTO;
import org.conectechgroup.conectech.model.Forum;
import org.conectechgroup.conectech.model.Post;
import org.conectechgroup.conectech.model.User;
import org.conectechgroup.conectech.service.ForumService;
import org.conectechgroup.conectech.service.PostService;
import org.conectechgroup.conectech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/forum")
public class ForumController {

    @Autowired
    private ForumService forumService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @GetMapping
public ResponseEntity<List<ForumDTO>> findAll() {
        List<Forum> forums = forumService.findAll();
        // use the convertToDTO method to convert the list of forums to a list of forumDTOs
        List<ForumDTO> forumDTOs = forums.stream().map(forumService::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(forumDTOs);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<ForumDTO> findById(@PathVariable String id) {
        Forum forum = forumService.findById(id);
        ForumDTO forumDTO = forumService.convertToDTO(forum);
        return ResponseEntity.ok().body(forumDTO);
    }

    @PostMapping(value = "/{userId}/create")
    public ResponseEntity<Forum> insert(@RequestBody Forum forum, @PathVariable String userId) {
        // Find the user by ID
        User user = userService.findById(userId);

        // If user is not found, return a not found response
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // Set the author of the forum
        forum.setAuthor(user);
        forum.setDate(new Date());

        // Insert the forum into the database
        Forum insertedForum = forumService.insert(forum, userId);

        // Return a response entity with the inserted forum
        return ResponseEntity.noContent().build();
    }

    //just will change the tittle and description
    @PutMapping(value = "/{id}")
    public ResponseEntity<Forum> update(@RequestBody Forum forum, @PathVariable String id) {
        Forum forum1 = forumService.findById(id);
        if (forum1 == null) {
            return ResponseEntity.notFound().build();
        }
        forum1.setTitle(forum.getTitle());
        forum1.setDescription(forum.getDescription());
        forumService.update(forum1);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        forumService.delete(id);
        return ResponseEntity.ok().build();
    }

    // Create a post on forum
    @PostMapping(value = "/{forumId}/post")
    public ResponseEntity<Forum> createPost(@RequestBody Post post, @PathVariable String forumId) {
        Forum forum = forumService.findById(forumId);

        if (forum == null) {
            return ResponseEntity.notFound().build();
        }
        post.setForum(forum);
        forum.getPosts().add(post);
        postService.insert(post);
        forumService.update(forum);

        return ResponseEntity.noContent().build();
    }

    // follow a forum
    @PostMapping(value = "/{userId}/follow/{forumId}")
    public ResponseEntity<Void> followForum(@PathVariable String userId, @PathVariable String forumId) {
        User user = userService.findById(userId);
        Forum forum = forumService.findById(forumId);

        if (user == null || forum == null) {
            return ResponseEntity.notFound().build();
        }
        user.getForums().add(forum);
        userService.update(user);
        forum.getParticipants().add(user);
        forumService.update(forum);

        return ResponseEntity.noContent().build();
    }
    // unfollow a forum
    @DeleteMapping(value = "/{userId}/unfollow/{forumId}")
    public ResponseEntity<Void> unfollowForum(@PathVariable String userId, @PathVariable String forumId) {
        User user = userService.findById(userId);
        Forum forum = forumService.findById(forumId);

        if (user == null || forum == null) {
            return ResponseEntity.notFound().build();
        }
        forum.getParticipants().remove(user);
        user.getForums().remove(forum);
        userService.update(user);
        forumService.update(forum);

        return ResponseEntity.noContent().build();
    }
    @PostMapping(value = "/{userId}/moderator/{forumId}")
    public ResponseEntity<Void> addModerator(@PathVariable String userId, @PathVariable String forumId) {
        User user = userService.findById(userId);
        Forum forum = forumService.findById(forumId);

        if (user == null || forum == null) {
            return ResponseEntity.notFound().build();
        }
        forum.getModerators().add(user);
        forumService.update(forum);

        return ResponseEntity.noContent().build();
    }
    @DeleteMapping(value = "/{userId}/moderator/{forumId}")
    public ResponseEntity<Void> removeModerator(@PathVariable String userId, @PathVariable String forumId) {
        User user = userService.findById(userId);
        Forum forum = forumService.findById(forumId);

        if (user == null || forum == null) {
            return ResponseEntity.notFound().build();
        }
        forum.getModerators().remove(user);
        forumService.update(forum);

        return ResponseEntity.noContent().build();
    }
    //Create a event on forum
    
}
