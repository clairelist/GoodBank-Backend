package com.revature.controllers;

import com.revature.annotations.Authorized;
import com.revature.models.CreditCard;
import com.revature.services.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/credit-card")
public class CreditCardController {

    @Autowired
    private CreditCardService creditCardService;

    @Authorized
    @GetMapping("/{id}")
    public ResponseEntity<List<CreditCard>> getCreditCards(@PathVariable("id") int userId) {
        Optional<List<CreditCard>> optional = creditCardService.findByUserId(userId);
        if(!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optional.get());
    }

}
