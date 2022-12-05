package com.revature.controllers;

import com.revature.annotations.Authorized;
import com.revature.dtos.AccountDTO;
import com.revature.dtos.TransactionDTO;
import com.revature.dtos.TransferDTO;
import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000", "http://good-bank-ui.s3-website-us-west-2.amazonaws.com"}, allowCredentials = "true", exposedHeaders = "Authorization")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Authorized
    @GetMapping("/{id}")
    public ResponseEntity<List<Account>> getAccounts(@PathVariable("id") int accountId) {
        List<Account> accounts = accountService.findByUserId(accountId);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @Authorized
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> createAccount(@RequestBody AccountDTO account, @RequestHeader("Authorization") String userId) {
        return ResponseEntity.ok(accountService.upsertAccount(account, userId));
    }

    @Authorized
    @GetMapping("/{id}/transaction")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable("id") int accountId) {
        return ResponseEntity.ok(accountService.getAllTransactions(accountId));
    }

    @Authorized
    @PostMapping(value = "/{id}/transaction", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> addTransaction(@PathVariable("id") int accountId, @RequestBody TransactionDTO transaction) {
        return new ResponseEntity<>(accountService.upsertTransaction(accountId, transaction), HttpStatus.CREATED);
    }

    @Authorized
    @PostMapping(value = "/{id}/transfer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Transaction>> addTransfer(@PathVariable("id") int accountId, @RequestBody TransferDTO transaction) {
        return new ResponseEntity<>(accountService.transferTransaction(accountId, transaction), HttpStatus.CREATED);
    }

}
