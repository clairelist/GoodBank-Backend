package com.revature.services;

import com.revature.dtos.ResetRequest;
import com.revature.models.User;
import com.revature.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(int id) {
        return userRepository.getById(id);
    }

    public Optional<User> findByCredentials(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) { return userRepository.save(user); }


    public User updatePassword(ResetRequest update) throws EntityNotFoundException {

        Optional<User> userByEmail;
        //Used to check if a user exists
        User updatedPass;
        //used to actually save the user and spit back out.
        userByEmail = findByEmail(update.getEmail());
        if (!userByEmail.isPresent()) {
            updatedPass = null;
        } else {
            try {
                User userById = findById(userByEmail.get().getId());
                userByEmail.get().setPassword(update.getPassword());
                updatedPass = userRepository.save(userById);
            } catch (EntityNotFoundException e) {
                return null;
            }

        }
        return updatedPass;
    }
}