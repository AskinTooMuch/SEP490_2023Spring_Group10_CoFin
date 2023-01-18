package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired private UserRepository repo;

    public List<User> listAll(){
        return (List<User>) repo.findAll();
    }

    public void save(User user){
        repo.save(user);
    }

    public User get(Integer id) throws UserNotFoundException {
        Optional<User> userGet = repo.findById(id);
        if (userGet.isPresent()) {
            return userGet.get();
        }
        throw new UserNotFoundException("Could not find any user with ID " + id);
    }

    public void delete(Integer id) throws UserNotFoundException {
        Long count = repo.countById(id);
        if (count == null || count == 0) {
            throw new UserNotFoundException("Could not find any user with ID " + id);
        }
        repo.deleteById(id);
    }

    public User login(String email, String password) throws UserNotFoundException {
        User userGet = repo.findByEmailAndPassword(email, password);
        if (userGet==null){
            throw new UserNotFoundException("Could not find any user with entered login information");
        }
        return userGet;
    }
}
