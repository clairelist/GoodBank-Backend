package com.revature.services;

import com.revature.dtos.AccountDTO;
import com.revature.dtos.TransactionDTO;
import com.revature.dtos.TransferDTO;
import com.revature.dtos.UserDTO;
import com.revature.exceptions.InsufficientFundsException;
import com.revature.exceptions.InvalidAccountException;
import com.revature.exceptions.InvalidInputException;
import com.revature.models.*;
import com.revature.repositories.AccountRepository;
import com.revature.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;
    private final TokenService tokenService;
    private final UserService userService;

    @Autowired
    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository, UserService userService, TokenService tokenService) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    public List<Account> findByUserId(int id) {
        User user = userService.findById(id);
        return accountRepository.findByUser(user);
    }

    public Account upsertAccount(AccountDTO accountToUpsertDTO, String userId) {
        Account accountToUpsert = new Account(accountToUpsertDTO);
        UserDTO currentUser = tokenService.extractTokenDetails(userId);
        User user = userService.findById(currentUser.getId());
        if (accountToUpsertDTO.getBalance() <= 0 || accountToUpsertDTO.getName().equals("")) {
            throw new InsufficientFundsException();
        }

        if(accountRepository.existsById(accountToUpsert.getId())) {
            Account account = accountRepository.getById(accountToUpsert.getId());
            account.setBalance(accountToUpsert.getBalance());
            account.setName(accountToUpsert.getName());
            return accountRepository.saveAndFlush(account);
        } else {
            Account newAccount = new Account();
            newAccount.setBalance(accountToUpsertDTO.getBalance());
            newAccount.setAccountType(accountToUpsertDTO.getAccountType());
            newAccount.setUser(user);
            newAccount.setName(accountToUpsertDTO.getName());
            newAccount.setCreationDate(Date.from(Instant.now()));
            accountRepository.save(newAccount);
            Transaction initialDeposit = new Transaction();
            initialDeposit.setAmount(newAccount.getBalance());
            initialDeposit.setDescription("Initial Deposit");
            initialDeposit.setType(TransactionType.INCOME);
            initialDeposit.setAccount(newAccount);
            initialDeposit.setCreationDate(Date.from(Instant.now()));
            transactionRepository.save(initialDeposit);
            return newAccount;
        }
    }

    public List<Transaction> upsertTransaction(int accountId, TransactionDTO transactionToUpsertDTO) {
        Transaction transactionToUpsert = new Transaction(transactionToUpsertDTO);
        Account account = accountRepository.getById(accountId);



        if(transactionToUpsert.getType() == TransactionType.EXPENSE) {
            account.setBalance(account.getBalance() - transactionToUpsert.getAmount());
        } else if (transactionToUpsert.getType() == TransactionType.INCOME) {
            account.setBalance(account.getBalance() + transactionToUpsert.getAmount());
        }
        accountRepository.saveAndFlush(account);
        transactionToUpsert.setAccount(account);
        transactionToUpsert.setCreationDate(Date.from(Instant.now()));
        transactionRepository.save(transactionToUpsert);
        return transactionRepository.findAllByAccountOrderByCreationDateDesc(account);
    }

    @Transactional
    public List<Transaction> transferTransaction(int accountId, TransferDTO transactionToTransferDTO) {
        Transaction transactionToTransfer = new Transaction();
        transactionToTransfer.setAmount(transactionToTransferDTO.getAmount());
        transactionToTransfer.setAccount(transactionToTransferDTO.getAccount());
        transactionToTransfer.setType(transactionToTransferDTO.getType());
        transactionToTransfer.setToAccountId(transactionToTransferDTO.getToAccountId());
        //grab both user accounts from initial request
        Account account = accountRepository.getById(accountId);
        Account toAccount = accountRepository.getById(transactionToTransfer.getToAccountId());
        //setup blank transaction to be filled in for receiver transaction to persist
        Transaction secondTransaction = new Transaction();
        //handle first transaction from initial sender
        if (transactionToTransfer.getAmount() > account.getBalance()) {
            throw new InsufficientFundsException();
        } else if (transactionToTransferDTO.getAccount().getId() == transactionToTransferDTO.getToAccountId()) {
            throw new InvalidAccountException();
        } else if (transactionToTransferDTO.getToAccountId().toString().trim().equals("")) {
            throw new InvalidAccountException();
        } else if (transactionToTransferDTO.getAmount() <= 0) {
            throw new InvalidInputException();
        } else {

                if (transactionToTransfer.getType() == TransactionType.TRANSFER) {
                    //set balance to amount minus the amount your sending and change to expense type.
                    account.setBalance(account.getBalance() - transactionToTransfer.getAmount());
                    transactionToTransfer.setType(TransactionType.EXPENSE);
                    //set balance of receiver account to add amount you are sending and change to income type.
                    toAccount.setBalance(toAccount.getBalance() + transactionToTransfer.getAmount());
                    secondTransaction.setType(TransactionType.INCOME);
                }
                ////////////////////accountRepository.saveAndFlush(account); //What does this do???
                //set remaining fields so that they are abstracted away from user
                transactionToTransfer.setAccount(account);
                transactionToTransfer.setToAccountId(toAccount.getId());
                transactionToTransfer.setDescription("Money sent to acct: " + toAccount.getId());
                transactionToTransfer.setCreationDate(Date.from(Instant.now()));
                transactionRepository.save(transactionToTransfer);

                //set fields for second transaction based off of what user submitted as long as validation allows.
                secondTransaction.setAmount(transactionToTransfer.getAmount());
                secondTransaction.setDescription("Money received from acct: " + account.getId());
                secondTransaction.setAccount(toAccount);
                secondTransaction.setCreationDate(Date.from(Instant.now()));
                transactionRepository.save(secondTransaction);
        }


        return transactionRepository.findAllByAccountOrderByCreationDateDesc(account);
    }
}
