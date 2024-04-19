package org.conectechgroup.conectech.controller;

import org.conectechgroup.conectech.model.Post;
import org.conectechgroup.conectech.model.PostDTO;
import org.conectechgroup.conectech.model.User;
import org.conectechgroup.conectech.model.Comment;
import org.conectechgroup.conectech.service.CommentService;
import org.conectechgroup.conectech.service.PostService;
import org.conectechgroup.conectech.service.UserService;
import org.conectechgroup.conectech.service.util.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/posts")
public class PostController {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<PostDTO> findById(@PathVariable String id) {
        Post post = postService.findById(id); // Parse String to Integer
        PostDTO postDTO = postService.convertToDTO(post);
        return ResponseEntity.ok().body(postDTO);
    }

    @PostMapping("/{id}/posts")
    public ResponseEntity<Void> addPostToUser(@PathVariable String id, @RequestBody Post post) {
        User user = userService.findById(id);
        post.setAuthor(user);
        post = postService.insert(post); // Save the post in the PostRepository
        user.getPosts().add(post);
        userService.update(user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> findAll() {
        List<Post> posts = postService.findALL();
        List<PostDTO> postDTOs = posts.stream()
                .map(postService::convertToDTO) // Fixed here
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(postDTOs);
    }

    @GetMapping(value = "/title")
    public ResponseEntity<List<PostDTO>> findByTitle(@RequestParam String title) {
        List<Post> posts = postService.findByTitle(title);
        List<PostDTO> postDTOs = posts.stream()
                .map(postService::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(postDTOs);
    }

    @GetMapping(value = "/fullsearch")
    public ResponseEntity<List<PostDTO>> fullSearch(
            @RequestParam(value="text", defaultValue="") String text,
            @RequestParam(value="minDate", defaultValue="") String minDate,
            @RequestParam(value="maxDate", defaultValue="") String maxDate) {
        text = URL.decodeParam(text);
        Date min = URL.convertDate(minDate, new Date(0L));
        Date max = URL.convertDate(maxDate, new Date());
        List<Post> posts = postService.fullSearch(text, min, max);
        List<PostDTO> postDTOs = posts.stream()
                .map(postService::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(postDTOs);
    }
    @PostMapping("/{id}/like/{postId}")
    public ResponseEntity<Void> likeOtherUserPost(@PathVariable String id, @PathVariable String postId) {
        // Encontra o usuário que está curtindo o post
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        // Encontra o post pelo ID
        Post post = postService.findById(postId);
        if (post == null) {
            return ResponseEntity.badRequest().build();
        }

        // Incrementa o contador de likes do post
        post.setLikes(post.getLikes() + 1);

        // Salva o post
        postService.save(post);

        // Retorna uma resposta de sucesso
        return ResponseEntity.noContent().build();
    }
    // Endpoint para descurtir o post de outro usuário
    @PostMapping("/{id}/dislike/{postId}")
    public ResponseEntity<Void> dislikeOtherUserPost(@PathVariable String id, @PathVariable String postId) {
        // Encontra o usuário que está descurtindo o post
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        // Encontra o post pelo ID
        Post post = postService.findById(postId);
        if (post == null) {
            return ResponseEntity.badRequest().build();
        }

        // Decrementa o contador de likes do post
        post.setLikes(post.getLikes() - 1);

        // Salva o post
        postService.save(post);

        // Retorna uma resposta de sucesso
        return ResponseEntity.noContent().build();
    }
    // Endpoint para comentar outro post
    @PostMapping("/{id}/posts/{postId}/comments")
    public ResponseEntity<Void> addCommentToPost(@PathVariable String id, @PathVariable String postId, @RequestBody Comment comment) {
        // Encontra o usuário que está comentando o post
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // Encontra o post pelo ID
        Post post = postService.findById(postId);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }

        // Cria um novo comentário com o conteúdo fornecido e define o autor como o usuário atual
        comment.setContent(comment.getContent());
        comment.setTitle(comment.getTitle());
        comment.setAuthor(user);
        comment.setDate(LocalDateTime.now()); // Define a data e hora atual

        // Define o post para o comentário
        comment.setPost(post);

        // Salva o comentário
        comment = commentService.insert(comment);

        // Adiciona o comentário ao post
        post.getComments().add(comment);

        // Salva o comentário
        commentService.save(comment);

        // Salva o post
        postService.save(post);

        // Retorna uma resposta de sucesso
        return ResponseEntity.noContent().build();
    }
}