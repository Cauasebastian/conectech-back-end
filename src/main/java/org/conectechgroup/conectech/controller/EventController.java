package org.conectechgroup.conectech.controller;

import org.conectechgroup.conectech.model.User;
import org.conectechgroup.conectech.DTO.EventDTO;
import org.conectechgroup.conectech.model.Event;
import org.conectechgroup.conectech.service.EventService;
import org.conectechgroup.conectech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    //CRUD operations
    @GetMapping(value = "/{id}")
    public ResponseEntity<EventDTO> findById(@PathVariable String id){
        Event event = eventService.findById(id);
        EventDTO eventDTO = eventService.convertToDTO(event);
        return ResponseEntity.ok().body(eventDTO);
    }
    @GetMapping
    public ResponseEntity<List<EventDTO>> findAll(){
        List<Event> events = eventService.findAll();
        List<EventDTO> eventDTOs = events.stream().map(eventService::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok().body(eventDTOs);
    }
    //insert with Posts inicialized in [] and user passed with id {Id}
    @PostMapping(value = "{id}/create")
    public ResponseEntity<EventDTO> insert(@RequestBody Event event, @PathVariable String id) {
        User author = userService.findById(id); // Retrieve the User object
        if (author == null) {
            return ResponseEntity.notFound().build(); // Return 404 if the user is not found
        }

        event.setAuthor(author); // Set the User object as the author
        event.setDate(new Date()); // Set the date to the current date

        // Add the author to the list of participants
        event.getParticipants().add(author);

        // Save the updated event
        Event newEvent = eventService.insert(event);

        // Add the event to the list of events participated in by the user
        userService.addEventToUser(id, newEvent);
        //Return the DTO
        EventDTO eventDTO = eventService.convertToDTO(newEvent);
        return ResponseEntity.ok().body(eventDTO);
    }
    //update only the tittle description and location
    @PutMapping(value = "/{id}")
    public ResponseEntity<EventDTO> update(@RequestBody Event event, @PathVariable String id){
        Event event1 = eventService.findById(id);
        if(event1 == null){
            return ResponseEntity.notFound().build();
        }
        event1.setTitle(event.getTitle());
        event1.setDescription(event.getDescription());
        event1.setLocation(event.getLocation());
        eventService.update(event1);
        EventDTO eventDTO = eventService.convertToDTO(event1);
        return ResponseEntity.ok().body(eventDTO);
    }
    //delete
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        if(eventService.findById(id) == null){
            return ResponseEntity.notFound().build();
        }
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }
    //add a participant to an event
    @PostMapping(value = "/{eventId}/participants/{userId}")
    public ResponseEntity<Void> addParticipant(@PathVariable String eventId, @PathVariable String userId){
        Event event = eventService.findById(eventId);
        User user = userService.findById(userId);
        if(event == null || user == null){
            return ResponseEntity.notFound().build();
        }
        if(event.getParticipants().contains(user)){
            return ResponseEntity.badRequest().build();
        }
        event.getParticipants().add(user);
        eventService.update(event);
        return ResponseEntity.noContent().build();
    }
    //remove a participant from an event
    @DeleteMapping(value = "/{eventId}/participants/{userId}")
    public ResponseEntity<Void> removeParticipant(@PathVariable String eventId, @PathVariable String userId){
        Event event = eventService.findById(eventId);
        User user = userService.findById(userId);
        if(event == null || user == null){
            return ResponseEntity.notFound().build();
        }
        if(!event.getParticipants().contains(user)){
            return ResponseEntity.badRequest().build();
        }
        event.getParticipants().remove(user);
        eventService.update(event);
        return ResponseEntity.noContent().build();
    }
    //find all participants of an event
    @GetMapping(value = "/{id}/participants")
    public ResponseEntity<List<User>> findParticipants(@PathVariable String id){
        Event event = eventService.findById(id);
        if(event == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(event.getParticipants());
    }
    // find event by tittle
    @GetMapping(value = "/title/{title}")
    public ResponseEntity<EventDTO> findByTitle(@PathVariable String title){
        Event event = eventService.findByTitle(title);
        EventDTO eventDTO = eventService.convertToDTO(event);
        return ResponseEntity.ok().body(eventDTO);
    }

    @PostMapping("/{id}/uploadImage")
    public ResponseEntity<String> uploadImage(@PathVariable String id, @RequestParam("image") MultipartFile imageFile) throws IOException, NoSuchAlgorithmException {
        System.out.println("Received request to upload image for event: " + id);
        eventService.saveEventWithImage(id, imageFile);
        return ResponseEntity.status(HttpStatus.OK).body("Image uploaded successfully");
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getEventImage(@PathVariable String id) {
        byte[] image = eventService.getEventImage(id);
        if (image != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }




}
