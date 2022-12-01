package com.revature.services;

import com.revature.dtos.LoanDTO;
import com.revature.dtos.TransactionDTO;
import com.revature.models.*;
import com.revature.repositories.AccountRepository;
import com.revature.repositories.LoanRepository;
import com.revature.repositories.TransactionRepository;
import com.revature.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.revature.models.Status.PENDING;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;



    public Optional<List<Account>> findByUserId(int id) {
        User user = userService.findById(id);
        return accountRepository.findByUser(user);
    }

    public Account upsertAccount(Account accountToUpsert, String userId) {

        int id = Integer.parseInt(userId);
        User user = userService.findById(id);

        if(accountRepository.existsById(accountToUpsert.getId())) {
            Account account = accountRepository.getById(accountToUpsert.getId());
            account.setBalance(accountToUpsert.getBalance());
            account.setName(accountToUpsert.getName());
            return accountRepository.saveAndFlush(account);
        } else {
            accountToUpsert.setUser(user);
            accountToUpsert.setCreationDate(Date.from(Instant.now()));
            return accountRepository.save(accountToUpsert);
        }
    }

    public List<Transaction> getAllTransactions(int accountId) {
        Account account = accountRepository.getById(accountId);
        return transactionRepository.findByAccount(account);
    }

    public Transaction upsertTransaction(int accountId, TransactionDTO transactionToUpsertDTO) {
        Transaction transactionToUpsert = new Transaction(transactionToUpsertDTO);
        Account account = accountRepository.getById(accountId);

        if(transactionToUpsert.getType() == TransactionType.Expense) {
            account.setBalance(account.getBalance() - transactionToUpsert.getAmount());
        } else if (transactionToUpsert.getType() == TransactionType.Income) {
            account.setBalance(account.getBalance() + transactionToUpsert.getAmount());
        }
        accountRepository.saveAndFlush(account);
        transactionToUpsert.setAccount(account);
        transactionToUpsert.setCreationDate(Date.from(Instant.now()));
        return transactionRepository.save(transactionToUpsert);
    }

    @Transactional
    public List<Transaction> transferTransaction(int accountId, TransactionDTO transactionToTransferDTO) {
        Transaction transactionToTransfer = new Transaction(transactionToTransferDTO);
        //grab both user accounts from initial request
        Account account = accountRepository.getById(accountId);
        Account toAccount = accountRepository.getById(transactionToTransfer.getToAccountId());
        //setup blank transaction to be filled in for receiver transaction to persist
        Transaction secondTransaction = new Transaction();
        //handle first transaction from initial sender
        if (transactionToTransfer.getAmount() > account.getBalance()) {
            System.out.println("TRANSFER AMOUNT: " + transactionToTransfer.getAmount());
            System.out.println("ACCOUNT BALANCE: " + account.getBalance());
            throw new RuntimeException();
        }

        if(transactionToTransfer.getType() == TransactionType.Transfer) {
            //set balance to amount minus the amount your sending and change to expense type.
            account.setBalance(account.getBalance() - transactionToTransfer.getAmount());
            transactionToTransfer.setType(TransactionType.Expense);
            //set balance of receiver account to add amount you are sending and change to income type.
            toAccount.setBalance(toAccount.getBalance() + transactionToTransfer.getAmount());
            secondTransaction.setType(TransactionType.Income);
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

        //create a list to return the transfers adding both initial request and second request.
        List<Transaction> transfers = new ArrayList<>();
        transfers.add(transactionToTransfer);
        transfers.add(secondTransaction);

        return transfers;
    }

}
