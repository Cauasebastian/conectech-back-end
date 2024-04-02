package org.conectechgroup.conectech.service;

import org.conectechgroup.conectech.model.Post;
import org.conectechgroup.conectech.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostService {
   @Autowired
    private PostRepository repo;
   public Post findById(Integer id){
       return repo.findById(id).orElse(null);
   }
   public List<Post> findALL(){
       return repo.findAll();
   }
    public List<Post> findByTitle(String tittle) {
        return repo.searchTitle(tittle);
    }

    public List<Post> fullSearch(String text, Date minDate, Date maxDate) {
        maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000);
        return repo.fullSearch(text, minDate, maxDate);
    }

}
