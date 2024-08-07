package org.conectechgroup.conectech.controller;

import org.conectechgroup.conectech.model.*;
import org.conectechgroup.conectech.DTO.CommentDTO;
import org.conectechgroup.conectech.DTO.PostDTO;
import org.conectechgroup.conectech.DTO.UserDTO;
import org.conectechgroup.conectech.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private EventService eventService;
    @Autowired
    private InterestService interestService;


    //#region Users

    /**
     * Retrieves all users in the DTO format.
     *
     * @return a ResponseEntity containing a list of all users
     */
    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> list = service.findAll();
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
    //getUserByName
    @RequestMapping(value= "/name/{name}", method=RequestMethod.GET)
    public ResponseEntity<UserDTO> findByName(@PathVariable String name) {
        User obj = service.findByName(name);
        UserDTO userDTO = service.convertToDTO(obj);
        return ResponseEntity.ok().body(userDTO);
    }

    /**
     * Inserts a new user.
     *
     * @param obj the user to be inserted
     * @return a ResponseEntity with no content
     */
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<User> insert(@RequestBody User obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    /**
     * Deletes a user by id.
     *
     * @param id the id of the user
     * @return a ResponseEntity with no content
     */
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
        public ResponseEntity<Void> delete(@PathVariable String id) {
        User user = service.findById(id); // Certifique-se de que findById é chamado
        if (user != null) {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
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
        service.updateProfile(obj);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/uploadImage")
    public ResponseEntity<String> uploadImage(@PathVariable String id, @RequestParam("image") MultipartFile imageFile) {
        try {
            service.saveUserWithImage(id, imageFile);
            return ResponseEntity.status(HttpStatus.OK).body("Image uploaded successfully");
        } catch (IOException | NoSuchAlgorithmException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getUserImage(@PathVariable String id) {
        byte[] image = service.getUserImage(id);
        if (image != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestParam("email") String email, @RequestParam("password") String password) {
        User user = service.findByEmailAndPassword(email, password);
        if (user != null) {
            UserDTO userDTO = service.convertToDTO(user);
            return ResponseEntity.ok().body(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //#endregion

    //#region Posts

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
     * Retrieves the posts of a user by id in the DTO format.
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
     * Precisa corrigir a formatação do Json .
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

        // 1. Fetch User and Post:
        User user = service.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build(); // User not found
        }

        Post postToDelete = user.getPosts().stream()
                .filter(p -> p.getId().equals(postId))
                .findFirst()
                .orElse(null);

        if (postToDelete == null) {
            return ResponseEntity.notFound().build(); // Post not found for this user
        }

        // 2. Remove the Post Reference:
        user.getPosts().remove(postToDelete);

        // 3. Update User and Delete Post:
        service.update(user);
        postService.delete(postId);

        return ResponseEntity.noContent().build(); // Success, no content needed
    }

    /**
     * Change The Controller to PostController
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

    //#endregion

    //#region Comments

    /**
     * Add a comment to a post.
     *
     * @param id      the id of the user
     * @param postId  the id of the post
     * @param comment the comment to be added
     * @return a ResponseEntity with no content
     */
    @PostMapping("/{id}/posts/{postId}/comments")
    public ResponseEntity<Void> addCommentToPost(@PathVariable String id, @PathVariable String postId, @RequestBody Comment comment) {
        User user = service.findById(id);
        Post post = user.getPosts().stream().filter(p -> p.getId().equals(postId)).findFirst().orElse(null);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        comment.setAuthor(user);
        comment.setDate(LocalDateTime.now()); // Set the current date and time
        comment.setPost(post); // Configura o post para o comentário

        // Salva o comentário
        comment = commentService.insert(comment);

        // Adiciona o comentário ao post
        post.getComments().add(comment);

        // Atualiza o post no banco de dados
        postService.save(post);

        // Retorna uma resposta de sucesso
        return ResponseEntity.noContent().build();
    }

   /**
     * Get all comments of a post.
     *
     * @param id     the id of the user
     * @param postId the id of the post
     * @return a ResponseEntity containing a list of comments
     */
    @GetMapping("/{id}/posts/{postId}/comments")
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable String id, @PathVariable String postId) {
        User user = service.findById(id);
        Post post = user.getPosts().stream().filter(p -> p.getId().equals(postId)).findFirst().orElse(null);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        List<CommentDTO> commentDTOs = post.getComments().stream()
                .map(commentService::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(commentDTOs);
    }

    /**
     * Get a comment by id.
     *
     * @param id        the id of the user
     * @param postId    the id of the post
     * @param commentId the id of the comment
     * @return a ResponseEntity containing the comment
     */
    @GetMapping("/{id}/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable String id, @PathVariable String postId, @PathVariable String commentId) {
        User user = service.findById(id);
        Post post = user.getPosts().stream().filter(p -> p.getId().equals(postId)).findFirst().orElse(null);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        Comment comment = post.getComments().stream().filter(c -> c.getId().equals(commentId)).findFirst().orElse(null);
        if (comment == null) {
            return ResponseEntity.notFound().build();
        }
        CommentDTO commentDTO = commentService.convertToDTO(comment);
        return ResponseEntity.ok().body(commentDTO);
    }

    /**
     * Update a comment.
     *
     * @param id        the id of the user
     * @param postId    the id of the post
     * @param commentId the id of the comment
     * @param comment   the comment to be updated
     * @return a ResponseEntity with no content
     */
    @PutMapping("/{id}/posts/{postId}/comments/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable String id, @PathVariable String postId, @PathVariable String commentId, @RequestBody Comment comment) {
        User user = service.findById(id);
        Post post = user.getPosts().stream().filter(p -> p.getId().equals(postId)).findFirst().orElse(null);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        Comment oldComment = post.getComments().stream().filter(c -> c.getId().equals(commentId)).findFirst().orElse(null);
        if (oldComment == null) {
            return ResponseEntity.notFound().build();
        }

        // Atualizar os campos do comentário existente
        oldComment.setTitle(comment.getTitle());
        oldComment.setContent(comment.getContent());
        oldComment.setDate(LocalDateTime.now()); // Atualizar a data do comentário para a data atual

        // Salvar o comentário atualizado
        commentService.save(oldComment);

        return ResponseEntity.noContent().build();
    }

    /**
     * Delete a comment.
     *
     * @param id        the id of the user
     * @param postId    the id of the post
     * @param commentId the id of the comment
     * @return a ResponseEntity with no content
     */
    @DeleteMapping("/{id}/posts/{postId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable String id, @PathVariable String postId, @PathVariable String commentId) {
        User user = service.findById(id);
        Post post = user.getPosts().stream().filter(p -> p.getId().equals(postId)).findFirst().orElse(null);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        Comment comment = post.getComments().stream().filter(c -> c.getId().equals(commentId)).findFirst().orElse(null);
        if (comment == null) {
            return ResponseEntity.notFound().build();
        }

        // Remover o comentário do post
        post.getComments().remove(comment);

        // Atualizar o post no banco de dados
        postService.save(post);

        // Deletar o comentário
        commentService.delete(commentId);

        return ResponseEntity.noContent().build();
    }

    /**
     * Like a comment.
     *
     * @param id        the id of the user
     * @param postId    the id of the post
     * @param commentId the id of the comment
     * @return a ResponseEntity with no content
     */
    @PostMapping("/{id}/posts/{postId}/comments/{commentId}/like")
    public ResponseEntity<Void> likeComment(@PathVariable String id, @PathVariable String postId, @PathVariable String commentId) {
        User user = service.findById(id);
        Post post = user.getPosts().stream().filter(p -> p.getId().equals(postId)).findFirst().orElse(null);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        Comment comment = post.getComments().stream().filter(c -> c.getId().equals(commentId)).findFirst().orElse(null);
        if (comment == null) {
            return ResponseEntity.notFound().build();
        }
        comment.setLikes(comment.getLikes() + 1);
        commentService.save(comment);
        return ResponseEntity.noContent().build();
    }

    //#endregion

    //#region Interests
    /**
     * Adicionar um interesse a um usuário
     *
     * @param id           the id of the user
     * @param interestName the name of the interest
     * @return a ResponseEntity with no content
     */
    @PostMapping("/{id}/interests/{interestName}")
    public ResponseEntity<Void> addInterestToUser(@PathVariable String id, @PathVariable String interestName) {
        User user = service.findById(id);
        Interest interest = interestService.findByName(interestName);
        if (interest == null) {
            return ResponseEntity.notFound().build();
        }
        service.addInterestToUser(id, interest);
        return ResponseEntity.noContent().build();
    }

    /**
     * Remover um interesse de um usuário
     *
     * @param id           the id of the user
     * @param interestName the name of the interest
     * @return a ResponseEntity with no content
     */
    @DeleteMapping("/{id}/interests/{interestName}")
    public ResponseEntity<Void> removeInterestFromUser(@PathVariable String id, @PathVariable String interestName) {
        User user = service.findById(id);
        Interest interest = interestService.findByName(interestName);
        if (interest == null) {
            return ResponseEntity.notFound().build();
        }
        service.removeInterestFromUser(id, interest);
        return ResponseEntity.noContent().build();
    }

    //#endregion

    /**
     * Adicionar um evento a um usuário
     *
     * @param id     the id of the user
     * @param eventId the id of the event
     * @return a ResponseEntity with no content
     */
    @PostMapping("/{id}/events/{eventId}")
    public ResponseEntity<Void> addEventToUser(@PathVariable String id, @PathVariable String eventId) {
        User user = service.findById(id);
        Event event = eventService.findById(eventId);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        service.addEventToUser(id, event);
        //update the user
        service.update(user);
        eventService.update(event);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/events/{eventId}")
    public ResponseEntity<Void> removeEventFromUser(@PathVariable String id, @PathVariable String eventId) {
        User user = service.findById(id);
        Event event = eventService.findById(eventId);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        service.removeEventFromUser(id, event);
        service.update(user);

        return ResponseEntity.noContent().build();
    }

    /**
     * Adicionar um seguidor a um usuário
     *
     * @param id     the id of the user
     * @param  userId the id of the event
     * @return a ResponseEntity with no content
     */
    @PostMapping("/{id}/follow/{userId}")
    public ResponseEntity<Void> followUser(@PathVariable String id, @PathVariable String userId) {
        User user = service.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        User userToFollow = service.findById(userId);
        if (userToFollow == null || userToFollow.equals(user) || user.getFollowing().contains(userToFollow)) {
            return ResponseEntity.badRequest().build();
        }
        user.getFollowing().add(userToFollow);
        userToFollow.getFollowers().add(user);
        service.updateFollower(user);
        service.updateFollower(userToFollow);
        return ResponseEntity.noContent().build();
    }

    /**
     * Remover um seguidor de um usuário
     *
     * @param id     the id of the user
     * @param  userId the id of the event
     * @return a ResponseEntity with no content
     */
    @DeleteMapping("/{id}/unfollow/{userId}")
    public ResponseEntity<Void> unfollowUser(@PathVariable String id, @PathVariable String userId) {
        User user = service.findById(id);
        User userToUnfollow = service.findById(userId);
        if (userToUnfollow == null) {
            return ResponseEntity.notFound().build();
        }
        user.getFollowing().remove(userToUnfollow);
        userToUnfollow.getFollowers().remove(user);
        service.update(user);
        service.update(userToUnfollow);
        return ResponseEntity.noContent().build();
    }
}