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

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) { return userRepository.save(user); }


    public User updatePassword(int userid, String update) throws EntityNotFoundException {

        User userById;
        //Used to check if a user exists

        User updatedPass;
        //used to actually save the user and spit back out.
        userById = findById(userid);

        if (userById == null) {
            updatedPass = null;
        } else {


            try {

                userById.setPassword(update); //TODO SAVE THE ACTUAL THING IN THE DATABASE!
                updatedPass = userRepository.save(userById);


            } catch (EntityNotFoundException e) {
                return null;
              //  throw new EntityNotFoundException();
            }

        }
        return updatedPass;
    }
}