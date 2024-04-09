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

    public Comment findById(Integer id){
        return commentRepository.findById(id).orElse(null);
    }

    public List<Comment> findAll(){
        return commentRepository.findAll();
    }

    public List<Comment> findByPostId(Integer postId){
        return commentRepository.findByPostId(postId);
    }

    public Comment insert(Comment comment){
        return commentRepository.insert(comment);
    }

    public void delete(Integer id){
        commentRepository.deleteById(id);
    }

    public Comment update(Comment comment){
        return commentRepository.save(comment);
    }
    public void deleteComment(Integer id) {
        commentRepository.deleteById(id);
    }
    public Comment updateComment(String id, Comment comment) {
        comment.setId(id);
        return commentRepository.save(comment);
    }
}