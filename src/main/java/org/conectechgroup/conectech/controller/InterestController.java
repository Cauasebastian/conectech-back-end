package org.conectechgroup.conectech.controller;

import org.conectechgroup.conectech.model.DTO.InterestDTO;
import org.conectechgroup.conectech.model.Interest;
import org.conectechgroup.conectech.service.InterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/interest")
public class InterestController {

    @Autowired
    private InterestService interestService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<InterestDTO> findById(@PathVariable String id){
        Interest interest = interestService.findById(id);
        InterestDTO interestDTO = interestService.convertToDTO(interest);
        return ResponseEntity.ok().body(interestDTO);
    }
    @GetMapping
    public ResponseEntity<List<InterestDTO>> findAll(){
        List<Interest> interests = interestService.findAll();
        List<InterestDTO> interestDTOs = interests.stream().map(interestService::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok().body(interestDTOs);
    }
    @GetMapping(value = "/name")
    public ResponseEntity<InterestDTO> findByName(@PathVariable String name){
        Interest interest = interestService.findByName(name);
        InterestDTO interestDTO = interestService.convertToDTO(interest);
        return ResponseEntity.ok().body(interestDTO);
    }
    @PostMapping
    public ResponseEntity<InterestDTO> insert(@RequestBody Interest interest){
        Interest newInterest = interestService.insert(interest);
        InterestDTO interestDTO = interestService.convertToDTO(newInterest);
        return ResponseEntity.ok().body(interestDTO);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        interestService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
