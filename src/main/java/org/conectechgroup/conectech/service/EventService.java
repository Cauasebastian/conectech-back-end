package org.conectechgroup.conectech.service;

import org.conectechgroup.conectech.DTO.EventDTO;
import org.conectechgroup.conectech.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.conectechgroup.conectech.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    //find by id
    public Event findById(String id){
        return eventRepository.findById(id).orElse(null);
    }
    //find all
    public List<Event> findAll(){
        return eventRepository.findAll();
    }
    //insert
    public Event insert(Event event){
        return eventRepository.save(event);
    }
    //update
    public Event update(Event event){
        return eventRepository.save(event);
    }
    //delete
    public void delete(String id){
        eventRepository.deleteById(id);
    }
    //convert to DTO
    public EventDTO convertToDTO(Event event){
       EventDTO eventDTO = new EventDTO();
         eventDTO.setId(event.getId());
            eventDTO.setTitle(event.getTitle());
            eventDTO.setDescription(event.getDescription());
            eventDTO.setDate(event.getDate());
            eventDTO.setLocation(event.getLocation());
            eventDTO.setAuthorName(event.getAuthor().getName());
            eventDTO.setParticipantsCount(event.getParticipants().size());
            eventDTO.setPostsCount(event.getPosts().size());
            return eventDTO;

        }
    //add post to event
    public void addPostToEvent(String id, org.conectechgroup.conectech.model.Post post){
        Event event = findById(id);
        event.getPosts().add(post);
        update(event);
    }

}