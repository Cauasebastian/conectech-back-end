package org.conectechgroup.conectech.controller;

import org.conectechgroup.conectech.model.Comment;
import org.conectechgroup.conectech.model.Post;
import org.conectechgroup.conectech.model.PostDTO;
import org.conectechgroup.conectech.model.User;
import org.conectechgroup.conectech.service.CommentService;
import org.conectechgroup.conectech.service.PostService;
import org.conectechgroup.conectech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

/**
 * UserController is a REST controller that handles HTTP requests related to User entities.
 */
@RestController
@RequestMapping(value="/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;

    /**
     * Adds a post to a user.
     *
     * @param id   the id of the user
     * @param post the post to be added
     * @return a ResponseEntity with no content
     */
    @PostMapping("/{id}/posts")
    public ResponseEntity<Void> addPostToUser(@PathVariable String id, @RequestBody Post post) {
        User user = service.findById(id);
        post.setAuthor(user);
        post = postService.insert(post); // Save the post in the PostRepository
        user.getPosts().add(post);
        service.update(user);
        service.addPostToUser(id, post); // Save the post in the UserRepository
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves all users.
     *
     * @return a ResponseEntity containing a list of all users
     */
    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<List<User>> findAll() {
        List<User> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    /**
     * Retrieves a user by id.
     *
     * @param id the id of the user
     * @return a ResponseEntity containing the user
     */
    @RequestMapping(value= "/{id}", method=RequestMethod.GET)
    public ResponseEntity<User> findById(@PathVariable String id) {
        User obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    /**
     * Inserts a new user.
     *
     * @param obj the user to be inserted
     * @return a ResponseEntity with no content
     */
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody User obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * Deletes a user by id.
     *
     * @param id the id of the user
     * @return a ResponseEntity with no content
     */
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Updates a user.
     *
     * @param obj the user to be updated
     * @param id  the id of the user
     * @return a ResponseEntity with no content
     */
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody User obj, @PathVariable String id) {
        obj.setId(id);
        service.update(obj);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves the posts of a user by id.
     *
     * @param id the id of the user
     * @return a ResponseEntity containing a list of the user's posts
     */
    @RequestMapping(value="/{id}/posts", method=RequestMethod.GET)
    public ResponseEntity<List<PostDTO>> getPosts(@PathVariable String id) {
        List<PostDTO> posts = service.getPosts(id);
        return ResponseEntity.ok().body(posts);
    }

    /**
     * Retrieves a post of a user by id.
     *
     * @param id     the id of the user
     * @param postId the id of the post
     * @return a ResponseEntity containing the post
     */
    @RequestMapping(value="/{id}/posts/{postId}", method=RequestMethod.GET)
    public ResponseEntity<Post> getPost(@PathVariable String id, @PathVariable String postId) {
        User user = service.findById(id);
        Post post = user.getPosts().stream().filter(p -> p.getId().equals(postId)).findFirst().orElse(null);
        return ResponseEntity.ok().body(post);
    }

    /**
     * Updates a post of a user by id.
     *
     * @param id     the id of the user
     * @param postId the id of the post
     * @param post   the post to be updated
     * @return a ResponseEntity with no content
     */
    @PutMapping("/{id}/posts/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable String id, @PathVariable String postId, @RequestBody Post post) {
        User user = service.findById(id);
        Post oldPost = user.getPosts().stream().filter(p -> p.getId().equals(postId)).findFirst().orElse(null);
        if (oldPost == null) {
            return ResponseEntity.notFound().build();
        }
        post.setId(postId);
        post.setAuthor(user);
        postService.save(post);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes a post of a user by id.
     *
     * @param id     the id of the user
     * @param postId the id of the post
     * @return a ResponseEntity with no content
     */
    @DeleteMapping("/{id}/posts/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable String id, @PathVariable String postId) {
        User user = service.findById(id);
        Post post = user.getPosts().stream().filter(p -> p.getId().equals(postId)).findFirst().orElse(null);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        user.getPosts().remove(post);
        service.update(user);
        postService.delete(postId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Likes a post of a user by id.
     *
     * @param id     the id of the user
     * @param postId the id of the post
     * @return a ResponseEntity with no content
     */
    @PostMapping("/{id}/posts/{postId}/like")
    public ResponseEntity<Void> likePost(@PathVariable String id, @PathVariable String postId) {
        User user = service.findById(id);
        Post post = user.getPosts().stream().filter(p -> p.getId().equals(postId)).findFirst().orElse(null);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        post.setLikes(post.getLikes() + 1);
        postService.save(post);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{id}/posts/{postId}/comment")
    public ResponseEntity<Void> addCommentToPost(@PathVariable String id, @PathVariable String postId, @RequestBody Comment comment) {
        User user = service.findById(id);
        Post post = user.getPosts().stream().filter(p -> p.getId().equals(postId)).findFirst().orElse(null);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        comment.setAuthor(user);
        comment.setDate(LocalDateTime.now()); // Set the current date and time
        comment.setPost(post);
        comment = commentService.insert(comment);

        post.getComments().add(comment);
        postService.save(post); // Save the post before saving the comment
        commentService.save(comment); // Save the comment after the post has been saved
        return ResponseEntity.noContent().build();
    }

}