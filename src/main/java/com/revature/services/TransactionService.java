package com.revature.services;

import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.repositories.AccountRepository;
import com.revature.repositories.TransactionPageRepository;
import com.revature.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionPageRepository transactionPageRepository;

    @Autowired
    public TransactionService(AccountRepository accountRepository, TransactionRepository transactionRepository, TransactionPageRepository transactionPageRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.transactionPageRepository = transactionPageRepository;
    }

    public List<Transaction> getAllTransactions(int accountId) {
        Account account = accountRepository.getById(accountId);
        return transactionRepository.findAllByAccountOrderByCreationDateDesc(account);
    }

    public List<Transaction> findFiveByAccountId(int accountId, int page) {
        Pageable pageWithFiveElements = PageRequest.of(page, 5, Sort.by("creationDate").descending());
        return transactionPageRepository.findAllByAccountId(accountId, pageWithFiveElements);
    }

    public Object getTransactionCount(int accountId) {
        Account account = accountRepository.getById(accountId);
        List<Transaction> trans;
        trans = transactionRepository.findAllByAccountOrderByCreationDateDesc(account);
        return trans.size();
    }
}
