package org.conectechgroup.conectech.repository;

import org.conectechgroup.conectech.model.Forum;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ForumRepository extends MongoRepository<Forum, String> {
    Forum findByTitle(String title);
}
