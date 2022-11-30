package com.revature.services;

import com.revature.models.User;
import com.revature.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(int id) {
        return userRepository.getById(id);
    }

    public Optional<User> findByCredentials(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User updatePassword (int userid, String update) throws EntityNotFoundException {

        User userById;
        //will have to see how they handle erroring; for now, lets assume that it returns nULL if no user
        //exists with that id

        try{
            userById = findById(userid);
            userById.setPassword(update); //TODO SAVE THE ACTUAL THING IN THE DATABASE!
        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException();
        }
        return userById;
    }
}
