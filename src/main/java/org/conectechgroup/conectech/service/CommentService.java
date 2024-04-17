package org.conectechgroup.conectech.service;

import org.conectechgroup.conectech.model.Comment;
import org.conectechgroup.conectech.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment findById(String id){
        return commentRepository.findById(id).orElse(null);
    }

    public List<Comment> findAll(){
        return commentRepository.findAll();
    }

    public List<Comment> findByPostId(String postId){
        return commentRepository.findByPostId(postId);
    }

    public Comment insert(Comment comment){
        return commentRepository.insert(comment);
    }

    public void delete(String id){
        commentRepository.deleteById(id);
    }

    public Comment update(Comment comment){
        return commentRepository.save(comment);
    }
    public void deleteComment(String id) {
        commentRepository.deleteById(id);
    }
    public Comment updateComment(String id, Comment comment) {
        comment.setId(id);
        return commentRepository.save(comment);
    }
    public Comment save(Comment comment){
        return commentRepository.save(comment);
    }
    public List<Comment> findByAuthorId(String authorId){
        return commentRepository.findByAuthorId(authorId);
    }


}