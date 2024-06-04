package org.conectechgroup.conectech.service;

import org.conectechgroup.conectech.DTO.EventDTO;
import org.conectechgroup.conectech.model.Event;
import org.conectechgroup.conectech.model.Image;
import org.conectechgroup.conectech.model.User;
import org.conectechgroup.conectech.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.conectechgroup.conectech.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ImageRepository imageRepository;


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
        User author = event.getAuthor();
        if (author != null) {
            eventDTO.setAuthorName(author.getName());
        } else {
            eventDTO.setAuthorName("Unknown");
        }
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
    //find by title
    public Event findByTitle(String title){
        return eventRepository.findByTitle(title);
    }

    public Event saveEventWithImage(String eventId, MultipartFile imageFile) throws IOException, NoSuchAlgorithmException {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();

            if (imageFile.isEmpty()) {
                throw new IllegalArgumentException("The image file is empty");
            }

            byte[] imageData = imageFile.getBytes();
            String contentType = imageFile.getContentType();

            if (contentType == null || !contentType.startsWith("image/")) {
                throw new IllegalArgumentException("The file is not a valid image");
            }

            String imageHash = calculateHash(imageData);
            Optional<Image> existingImage = imageRepository.findByHash(imageHash);

            Image image;
            if (existingImage.isPresent()) {
                image = existingImage.get();
            } else {
                image = new Image(imageData, contentType, imageHash);
                imageRepository.save(image);
            }

            event.setImage(image);
            eventRepository.save(event);

            return event;
        } else {
            throw new RuntimeException("Event not found");
        }
    }

    public byte[] getEventImage(String eventId) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            Image image = event.getImage();
            return image != null ? image.getData() : null;
        } else {
            throw new RuntimeException("Event not found");
        }
    }

    private String calculateHash(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(data);
        return Base64.getEncoder().encodeToString(hashBytes);
    }

}