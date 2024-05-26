package org.conectechgroup.conectech.repository;

import org.conectechgroup.conectech.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ImageRepository extends MongoRepository<Image, String> {
    Optional<Image> findByHash(String hash);
}
