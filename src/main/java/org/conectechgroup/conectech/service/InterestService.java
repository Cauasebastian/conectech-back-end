package org.conectechgroup.conectech.service;

import org.conectechgroup.conectech.DTO.InterestDTO;
import org.conectechgroup.conectech.model.Interest;
import org.conectechgroup.conectech.repository.InterestRepository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class InterestService {
    @Autowired
    private InterestRepository interestRepository;

    public Interest findById(String id) {
        return interestRepository.findById(id).orElse(null);
    }
    public java.util.List<Interest> findAll() {
        return interestRepository.findAll();
    }
    public Interest findByName(String name) {
        return interestRepository.findByName(name);
    }
    public Interest insert(Interest interest) {
        return interestRepository.save(interest);
    }
    public Interest update(Interest interest) {
        return interestRepository.save(interest);
    }
    public void delete(String id) {
        interestRepository.deleteById(id);
    }
    public InterestDTO convertToDTO(Interest interest) {
        InterestDTO interestDTO = new InterestDTO();
        interestDTO.setName(interest.getName());
        interestDTO.setPostsCount(interest.getPosts().size());
        return interestDTO;
    }


    //add post to interest
    public void addPostToInterest(String Name, org.conectechgroup.conectech.model.Post post) {
        Interest interest = findByName(Name);
        interest.getPosts().add(post);
        update(interest);
    }
}
