package com.revature.services;

import com.revature.dtos.NotificationCreationRequest;
import com.revature.dtos.ResetRequest;
import com.revature.dtos.UserDTO;
import com.revature.dtos.UpdateRequest;
import com.revature.exceptions.CheckRegisterFieldsException;
import com.revature.exceptions.InvalidInputException;
import com.revature.exceptions.InvalidLoginException;
import com.revature.exceptions.PasswordUnderAmountException;
import com.revature.models.Notification;
import com.revature.models.User;
import com.revature.repositories.NotificationRepository;
import com.revature.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, NotificationRepository notificationRepository) {
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User findById(int id) {
        return userRepository.getById(id);
    }

    public UserDTO loginCreds(String email, String password) {
        //email below will return all lowercase even if added as caps
        Optional<User> fullUser = userRepository.findByEmail(email);
        if (fullUser.isPresent()) {
            if (!this.passwordEncoder.matches(password, fullUser.get().getPassword())){
                throw new InvalidLoginException();
            }
            return new UserDTO(
                    fullUser.get().getId(),
                    fullUser.get().getEmail(),
                    fullUser.get().getFirstName(),
                    fullUser.get().getLastName(),
                    fullUser.get().getAddress(),
                    fullUser.get().getState(),
                    fullUser.get().getCity(),
                    fullUser.get().getZip(),
                    fullUser.get().getUserType()
            );
        }
        else {
            throw new InvalidLoginException();
        }
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) { return userRepository.save(user); }


    public User updatePassword(ResetRequest update) {
        Optional<User> user = userRepository.findByEmail(update.getEmail());
        if ((update.getEmail().trim().equals("")
                || update.getPassword().trim().equals("")
                || update.getConfirmPassword().trim().equals(""))) {
            throw new CheckRegisterFieldsException(); // checks for missing/empty fields
        } else if (update.getPassword().length() <= 3 && update.getConfirmPassword().length() <= 3) { // creates min length requirement
            throw new PasswordUnderAmountException();
        } else if(!update.getPassword().equals(update.getConfirmPassword())) {
            throw new InvalidInputException();
        } else {

            if (!user.isPresent()) {
                return null;
            } else {
                User currentUser = user.get();
                currentUser.setPassword(this.passwordEncoder.encode(update.getPassword()).trim());
                return userRepository.save(currentUser);
            }
        }
    }

    public UserDTO updateProfile(UpdateRequest updateRequest) throws EntityNotFoundException {

        User updatedProfile;
        UserDTO updatedDTO = null;

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
                updatedDTO = new UserDTO(updatedProfile);

                NotificationCreationRequest request = new NotificationCreationRequest(
                        userById,
                        "Your user profile has successfully been updated!"
                );
                notificationRepository.save(new Notification(request));

            } catch (EntityNotFoundException e) {
                return null;
            }
        }
        return updatedDTO;
    }
}