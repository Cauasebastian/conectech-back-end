package org.conectechgroup.conectech.repository;

import org.conectechgroup.conectech.model.Interest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InterestRepository extends MongoRepository<Interest, String> {
    Interest findByName(String name);


}
