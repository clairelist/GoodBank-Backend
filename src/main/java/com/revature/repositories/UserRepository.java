package com.revature.repositories;

import com.revature.dtos.UserDTO;
import com.revature.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    UserDTO findByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String email);

}
