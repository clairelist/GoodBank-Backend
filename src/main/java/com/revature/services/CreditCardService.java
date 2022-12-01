package com.revature.services;

import com.revature.models.Account;
import com.revature.models.CreditCard;
import com.revature.models.User;
import com.revature.repositories.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreditCardService {

    @Autowired
    private UserService userService;

    @Autowired
    private CreditCardRepository creditCardRepository;

    public Optional<List<CreditCard>> findByUserId(int id) {
        User user = userService.findById(id);
        return creditCardRepository.findByUser(user);
    }
}
