package com.revature.services;

import com.revature.dtos.ResetRequest;
import com.revature.dtos.UserDTO;
import com.revature.dtos.UpdateRequest;
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

    public UserDTO loginCreds(String email, String password) {
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

    public User updateProfile(UpdateRequest updateRequest) throws EntityNotFoundException {

        User updatedProfile;

        User userById = findById(updateRequest.getId());
        if (userById == null) {
            updatedProfile = null;
        } else {
            try {
                userById.setFirstName(updateRequest.getFirstName());
                userById.setLastName(updateRequest.getLastName());
                userById.setEmail(updateRequest.getEmail());
                userById.setAddress(updateRequest.getAddress());
                userById.setCity(updateRequest.getCity());
                userById.setState(updateRequest.getState());
                userById.setZip(updateRequest.getZip());
                updatedProfile = userRepository.save(userById);
            } catch (EntityNotFoundException e) {
                return null;
            }
        }
        return updatedProfile;
    }
}