package org.conectechgroup.conectech.repository;

import org.conectechgroup.conectech.model.Image;
import org.conectechgroup.conectech.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByEmailAndPassword(String email, String password);

    User findByName(String name);

    List<User> findByProfileImage(Image profileImage);
}
