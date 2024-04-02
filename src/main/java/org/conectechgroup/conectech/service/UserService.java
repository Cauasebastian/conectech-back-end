package org.conectechgroup.conectech.service;

import com.mongodb.DuplicateKeyException;
import org.conectechgroup.conectech.model.User;
import org.conectechgroup.conectech.repository.UserRepository;
import org.conectechgroup.conectech.service.exception.ObjectNotFoundException;
import org.conectechgroup.conectech.service.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    public List<User> findAll() {
        return repo.findAll();
    }

    public User findById(Integer id) {
        return repo.findById(id.toString())
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public User insert(User obj) {
        try {
            return repo.insert(obj);
        } catch (DuplicateKeyException e) {
            throw new UserAlreadyExistsException("User already exists with id: " + obj.getId());
        }
    }

    public void delete(Integer id) {
        findById(id);
        repo.deleteById(id.toString());
    }

    public User update(User obj) {
        User newObj = findById(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    private void updateData(User newObj, User obj) {
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
        newObj.setDateOfBirth(obj.getDateOfBirth());
        newObj.setCpfcnpj(obj.getCpfcnpj());
        newObj.setPassword(obj.getPassword());
        newObj.setPosts(obj.getPosts());
    }
}