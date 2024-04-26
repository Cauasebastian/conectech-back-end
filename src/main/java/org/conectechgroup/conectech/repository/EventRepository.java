package org.conectechgroup.conectech.repository;

import org.conectechgroup.conectech.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

@org.springframework.stereotype.Repository
public interface EventRepository extends MongoRepository<Event, String> {

}
