package org.conectechgroup.conectech.repository;

import org.conectechgroup.conectech.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // Add custom query methods here if needed
}