package org.conectechgroup.conectech.service;

import org.conectechgroup.conectech.model.Forum;
import org.conectechgroup.conectech.DTO.ForumDTO;
import org.conectechgroup.conectech.DTO.PostDTO;
import org.conectechgroup.conectech.model.Post;
import org.conectechgroup.conectech.repository.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ForumService {

    @Autowired
    private ForumRepository forumRepository;

    public Forum findById(String id) {
        return forumRepository.findById(id).orElse(null);
    }
    public java.util.List<Forum> findAll() {
        return forumRepository.findAll();
    }
    public Forum insert(Forum forum, String userId) {
        return forumRepository.save(forum);
    }

    public Forum update(Forum forum) {
        return forumRepository.save(forum);
    }
    public void delete(String id) {
        forumRepository.deleteById(id);
    }
    public Forum findByTitle(String title) {
        return forumRepository.findByTitle(title);
    }

    //convert to DTO
    public ForumDTO convertToDTO(Forum forum) {
        ForumDTO forumDTO = new ForumDTO();
        forumDTO.setId(forum.getId());
        forumDTO.setTitle(forum.getTitle());
        forumDTO.setDescription(forum.getDescription());
        forumDTO.setCreationDate(forum.getDate());
        forumDTO.setAuthorName(forum.getAuthor().getName());
        forumDTO.setPosts(forum.getPosts().stream().map(Post::getTitle).collect(Collectors.toList()));
        forumDTO.setModerators(forum.getModerators().stream().map(user -> user.getName()).collect(Collectors.toList()));
        forumDTO.setParticipants(forum.getParticipants().stream().map(user -> user.getName()).collect(Collectors.toList()));
        forumDTO.setEvents(forum.getEvents().stream().map(event -> event.getTitle()).collect(Collectors.toList()));
        return forumDTO;
    }


    public Forum insert(Forum forum) {
        return forumRepository.save(forum);
    }
}
