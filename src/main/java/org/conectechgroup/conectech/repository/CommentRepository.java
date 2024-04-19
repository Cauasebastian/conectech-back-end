package org.conectechgroup.conectech.repository;

import org.conectechgroup.conectech.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByPostId(String postId);

    List<Comment> findByAuthorId(String authorId);

    //find by postid
    
    

}