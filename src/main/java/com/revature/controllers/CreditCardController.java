package com.revature.controllers;

import com.revature.dtos.CreditCardTransactionDTO;
import com.revature.models.CreditCard;
import com.revature.models.CreditCardTransaction;
import com.revature.services.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/credit-card")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000", "http://good-bank-ui.s3-website-us-west-2.amazonaws.com"}, allowCredentials = "true", exposedHeaders = "Authorization")
public class CreditCardController {

    @Autowired
    private CreditCardService creditCardService;

    @GetMapping("/{id}")
    public ResponseEntity<List<CreditCard>> getCreditCards(@PathVariable("id") int userId) {
        List<CreditCard> optional = creditCardService.findByUserId(userId);
        return ResponseEntity.ok(optional);
    }

    @PostMapping("/{id}/payment")
    public ResponseEntity<List<CreditCardTransaction>> makeCreditCardPayment(@PathVariable("id") int userId, @RequestBody CreditCardTransactionDTO creditCardTransactionDTO) {
        List<CreditCardTransaction> ccTransactions = creditCardService.makeCreditCardPayment(userId, creditCardTransactionDTO);
        if(ccTransactions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ccTransactions);
    }

}
