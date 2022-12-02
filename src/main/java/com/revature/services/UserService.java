package com.revature.services;

import com.revature.models.User;
import com.revature.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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


    public User updatePassword(User update) throws EntityNotFoundException {

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
               // userByEmail.setPassword(update.getPassword());

                updatedPass = userRepository.save(userById);
            } catch (EntityNotFoundException e) {
                return null;
            }

        }
        return updatedPass;
    }

//    public void sendEmail(String email){
//        //call mail service, send email with the email as recipient
//        String recipient = email;
//        int id = 0;
//        String message = "You requested a password reset, click here (or copy and paste into your browser) http://react-app.com/reset-password/" + id + " to reset your password."; //ID SHOULD BE ENCRYPTED!
//        String sender = "donotreply@goodbank.com";
//
//        try {
//            Optional<User> foundUser = findByEmail(email);
//            if(foundUser.isPresent()){
//                //call mail service here, suppling message, sender, recipeitn, id etc
//                id = foundUser.get().getId(); //TODO: I NEED TO BE ENCRYPTED, THEN DECRYPTED WHEN USER SENDS PATCH REQUEST
//                //MailService ms;
//                //ms.send?(recipient, sender, message);
//            }
//        } catch (EntityNotFoundException e){
//            e.printStackTrace(); //deprecate me?
//        }
//    }
}