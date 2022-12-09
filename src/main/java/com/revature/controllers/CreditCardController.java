package com.revature.controllers;

import com.revature.annotations.Secured;
import com.revature.dtos.CreditCardTransactionDTO;
import com.revature.models.CreditCard;
import com.revature.models.CreditCardTransaction;
import com.revature.services.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Secured(rolesAllowed = { "ADMIN", "CLIENT" })
    @GetMapping("/{id}")
    public ResponseEntity<List<CreditCard>> getCreditCards(@PathVariable("id") int userId) {
        Optional<List<CreditCard>> optional = creditCardService.findByUserId(userId);
        if(!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optional.get());
    }

    @Secured(rolesAllowed = { "ADMIN", "CLIENT" })
    @PostMapping("/{id}/payment")
    public ResponseEntity<List<CreditCardTransaction>> makeCreditCardPayment(@PathVariable("id") int userId, @RequestBody CreditCardTransactionDTO creditCardTransactionDTO) {
        List<CreditCardTransaction> ccTransactions = creditCardService.makeCreditCardPayment(userId, creditCardTransactionDTO);
        if(ccTransactions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ccTransactions);
    }

    @Secured(rolesAllowed = {"ADMIN", "CLIENT"})
    @PostMapping("/credit-card-application")
    public ResponseEntity<CreditCard> appliedCreditCard( @RequestBody int userId, int totalLimit) {
        System.out.println(userId);
        System.out.println(totalLimit);
        CreditCard newCreditCard = creditCardService.createCCApplication( userId, totalLimit);
        return new ResponseEntity<>(newCreditCard, HttpStatus.CREATED);
    }
}
