package com.revature.controllers;

import com.revature.annotations.Secured;
import com.revature.dtos.CreditCardApplicationDTO;
import com.revature.dtos.CreditCardDTO;
import com.revature.dtos.CreditCardTransactionDTO;
import com.revature.models.CreditCard;
import com.revature.models.CreditCardTransaction;
import com.revature.services.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/credit-card")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000", "http://good-bank-ui.s3-website-us-west-2.amazonaws.com"}, allowCredentials = "true", exposedHeaders = "Authorization")
public class CreditCardController {

    @Autowired
    private CreditCardService creditCardService;

    @Secured(rolesAllowed = { "ADMIN", "CLIENT" })
    @GetMapping("/{id}")
    public ResponseEntity<List<CreditCard>> getCreditCards(@PathVariable("id") int userId) {
        List<CreditCard> optional = creditCardService.findByUserId(userId);
        return ResponseEntity.ok(optional);
    }

    @Secured(rolesAllowed = { "ADMIN", "CLIENT" })
    @PostMapping("/{id}/payment")
    public ResponseEntity<Double> makeCreditCardPayment(@PathVariable("id") int userId, @RequestBody CreditCardTransactionDTO creditCardTransactionDTO) {
        Double accountBalance = creditCardService.makeCreditCardPayment(userId, creditCardTransactionDTO);
        if(accountBalance == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(accountBalance);
    }

    @Secured(rolesAllowed = {"ADMIN", "CLIENT"})
    @PostMapping("/credit-card-application")
    public ResponseEntity<CreditCard> appliedCreditCard(@RequestBody CreditCardApplicationDTO totalLimit, @RequestHeader("Authorization") String userId) {
        CreditCard newCreditCard = creditCardService.createCCApplication(userId, totalLimit.getInitialAmount());
        return new ResponseEntity<>(newCreditCard, HttpStatus.CREATED);
    }

    @Secured(rolesAllowed = { "ADMIN", "CLIENT" })
    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<CreditCardTransaction>> getCreditCardTransactions(@PathVariable("id") int creditCardId, @RequestHeader("Authorization") String userId) {
        List<CreditCardTransaction> transactions = creditCardService.getTransactionsByCreditCardId(creditCardId, userId);
        return ResponseEntity.ok(transactions);
    }

    @Secured(rolesAllowed = { "ADMIN", "CLIENT" })
    @GetMapping("/get-pending")
    public ResponseEntity<List<CreditCard>> getPendingCreditCards(@RequestHeader("Authorization") String userId) {
        List<CreditCard> creditcards = creditCardService.getPendingCreditCards(userId);
        return ResponseEntity.ok(creditcards);
    }

    @Secured(rolesAllowed = { "ADMIN", "CLIENT" })
    @PutMapping("/update-status")
    public ResponseEntity<CreditCard> updateCreditCardStatus(@RequestHeader("Authorization") String userId, @RequestBody CreditCardDTO creditCard) {
        CreditCard updatedCreditCard = creditCardService.updateCreditCardStatus(userId, creditCard);
        return ResponseEntity.ok(updatedCreditCard);
    }
}
