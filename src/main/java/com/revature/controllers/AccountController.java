package com.revature.controllers;

import com.revature.annotations.Secured;
import com.revature.dtos.AccountDTO;
import com.revature.dtos.TransactionDTO;
import com.revature.dtos.TransferDTO;
import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.services.AccountService;
import com.revature.services.TokenService;
import com.revature.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000", "http://good-bank-ui.s3-website-us-west-2.amazonaws.com"}, allowCredentials = "true", exposedHeaders = "Authorization")
public class AccountController {

    private AccountService accountService;

    private TransactionService transactionService;

    private TokenService tokenService;

    @Autowired
    public AccountController(AccountService accountService, TransactionService transactionService, TokenService tokenService) {
        this.accountService = accountService;
        this.tokenService = tokenService;
        this.transactionService = transactionService;
    }

    @Secured(rolesAllowed = { "ADMIN", "CLIENT" })
    @GetMapping("/{id}")
    public ResponseEntity<List<Account>> getAccounts(@PathVariable("id") int accountId) {
        List<Account> accounts = accountService.findByUserId(accountId);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @Secured(rolesAllowed = { "ADMIN", "CLIENT" })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> createAccount(@RequestBody AccountDTO account, @RequestHeader("Authorization") String userId) {
        return ResponseEntity.ok(accountService.upsertAccount(account, userId));
    }

    @Secured(rolesAllowed = { "ADMIN", "CLIENT" })
    @GetMapping("/{id}/transaction")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable("id") int accountId) {
        return ResponseEntity.ok(transactionService.getAllTransactions(accountId));
    }

    @Secured(rolesAllowed = { "ADMIN", "CLIENT" })
    @GetMapping("/{id}/transaction/{page}")
    public ResponseEntity<List<Transaction>> findFiveByAccountId(@PathVariable("id") int accountId, @PathVariable("page") int page) {
        return ResponseEntity.ok(transactionService.findFiveByAccountId(accountId, page));
    }

    @Secured(rolesAllowed = { "ADMIN", "CLIENT" })
    @GetMapping("/{id}/transactions")
    public ResponseEntity<Object> getTransactionCount(@PathVariable("id") int accountId) {
        return ResponseEntity.ok(transactionService.getTransactionCount(accountId));
    }

    @Secured(rolesAllowed = { "ADMIN", "CLIENT" })
    @PostMapping(value = "/{id}/transaction", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Transaction>> addTransaction(@PathVariable("id") int accountId, @RequestBody TransactionDTO transaction) {
        if (transaction == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accountService.upsertTransaction(accountId, transaction), HttpStatus.CREATED);
    }

    @Secured(rolesAllowed = { "ADMIN", "CLIENT" })
    @PostMapping(value = "/{id}/transfer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Transaction>> addTransfer(@PathVariable("id") int accountId, @RequestBody TransferDTO transaction) {
        if (transaction == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accountService.transferTransaction(accountId, transaction), HttpStatus.CREATED);
    }

}
