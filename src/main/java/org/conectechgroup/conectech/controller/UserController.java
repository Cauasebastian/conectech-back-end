package org.conectechgroup.conectech.controller;

import org.conectechgroup.conectech.model.Post;
import org.conectechgroup.conectech.model.User;
import org.conectechgroup.conectech.service.PostService;
import org.conectechgroup.conectech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value="/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private PostService postService;

    @PostMapping("/{id}/posts")
    public ResponseEntity<Void> addPostToUser(@PathVariable String id, @RequestBody Post post) {
        User user = service.findById(id);
        post = postService.insert(post); // Save the post in the PostRepository
        user.getPosts().add(post);
        service.update(user);
        service.addPostToUser(id, post); // Save the post in the UserRepository
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<List<User>> findAll() {
        List<User> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value= "/{id}", method=RequestMethod.GET)
    public ResponseEntity<User> findById(@PathVariable String id) {
        User obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody User obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody User obj, @PathVariable String id) {
        obj.setId(id);
        service.update(obj);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}/posts", method=RequestMethod.GET)
    public ResponseEntity<List<Post>> getPosts(@PathVariable String id) {
        User obj = service.findById(id);
        return ResponseEntity.ok().body(obj.getPosts());
    }
}