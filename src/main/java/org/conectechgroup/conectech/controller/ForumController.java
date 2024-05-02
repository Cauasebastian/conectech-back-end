package org.conectechgroup.conectech.controller;

import org.conectechgroup.conectech.DTO.ForumDTO;
import org.conectechgroup.conectech.model.Event;
import org.conectechgroup.conectech.model.Forum;
import org.conectechgroup.conectech.model.Post;
import org.conectechgroup.conectech.model.User;
import org.conectechgroup.conectech.service.EventService;
import org.conectechgroup.conectech.service.ForumService;
import org.conectechgroup.conectech.service.PostService;
import org.conectechgroup.conectech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ForumController is a REST controller that handles HTTP requests related to forums.
 */
@RestController
@RequestMapping(value = "/forum")
public class ForumController {

    @Autowired
    private ForumService forumService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private EventService eventService;

    /**
     * Get all forums.
     * @return A list of all forums as ForumDTOs.
     */
    @GetMapping
    public ResponseEntity<List<ForumDTO>> findAll() {
        List<Forum> forums = forumService.findAll();
        List<ForumDTO> forumDTOs = forums.stream().map(forumService::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(forumDTOs);
    }

    /**
     * Get a forum by its ID.
     * @param id The ID of the forum.
     * @return The forum as a ForumDTO.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<ForumDTO> findById(@PathVariable String id) {
        Forum forum = forumService.findById(id);
        ForumDTO forumDTO = forumService.convertToDTO(forum);
        return ResponseEntity.ok().body(forumDTO);
    }

    /**
     * Create a new forum.
     * @param forum The forum to be created.
     * @param userId The ID of the user creating the forum.
     * @return A ResponseEntity indicating the result of the operation.
     */
    @PostMapping(value = "/{userId}/create")
    public ResponseEntity<Forum> insert(@RequestBody Forum forum, @PathVariable String userId) {
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        forum.setAuthor(user);
        forum.setDate(new Date());
        Forum insertedForum = forumService.insert(forum, userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Update a forum.
     * @param forum The updated forum.
     * @param id The ID of the forum to be updated.
     * @return A ResponseEntity indicating the result of the operation.
     */
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

    /**
     * Delete a forum.
     * @param id The ID of the forum to be deleted.
     * @return A ResponseEntity indicating the result of the operation.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        forumService.delete(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Create a post in a forum.
     * @param post The post to be created.
     * @param forumId The ID of the forum where the post will be created.
     * @return A ResponseEntity indicating the result of the operation.
     */
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

    /**
     * Follow a forum.
     * @param userId The ID of the user who will follow the forum.
     * @param forumId The ID of the forum to be followed.
     * @return A ResponseEntity indicating the result of the operation.
     */
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

    /**
     * Unfollow a forum.
     * @param userId The ID of the user who will unfollow the forum.
     * @param forumId The ID of the forum to be unfollowed.
     * @return A ResponseEntity indicating the result of the operation.
     */
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

    /**
     * Add a moderator to a forum.
     * @param userId The ID of the user who will be added as a moderator.
     * @param forumId The ID of the forum where the user will be added as a moderator.
     * @return A ResponseEntity indicating the result of the operation.
     */
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

    /**
     * Remove a moderator from a forum.
     * @param userId The ID of the user who will be removed as a moderator.
     * @param forumId The ID of the forum where the user will be removed as a moderator.
     * @return A ResponseEntity indicating the result of the operation.
     */
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
    /* create a event on a forum */
    @PostMapping(value = "/{forumId}/event/{userId}")
    public ResponseEntity<Void> addEvent(@RequestBody Event event, @PathVariable String forumId, @PathVariable String userId) {
        Forum forum = forumService.findById(forumId);
        User user = userService.findById(userId);
        if (forum == null || user == null) {
            return ResponseEntity.notFound().build();
        }
        event.setAuthor(user);
        event.setForum(forum);
        event.setDate(new Date());
        forum.getEvents().add(event);
        eventService.insert(event);
        forumService.update(forum);
        return ResponseEntity.noContent().build();
    }

    /* delete a event from a forum */
    @DeleteMapping(value = "/{forumId}/event/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String forumId, @PathVariable String eventId) {
        Forum forum = forumService.findById(forumId);
        Event event = eventService.findById(eventId);
        if (forum == null || event == null) {
            return ResponseEntity.notFound().build();
        }
        forum.getEvents().remove(event);
        eventService.delete(eventId);
        forumService.update(forum);
        return ResponseEntity.noContent().build();
    }
}