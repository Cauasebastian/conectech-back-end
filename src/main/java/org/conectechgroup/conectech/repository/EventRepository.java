package org.conectechgroup.conectech.repository;

import org.conectechgroup.conectech.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {

}
