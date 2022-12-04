package com.revature.services;

import com.revature.dtos.CreditCardTransactionDTO;
import com.revature.exceptions.NotLoggedInException;
import com.revature.models.*;
import com.revature.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CreditCardService {

    @Autowired
    private UserService userService;
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CreditCardTransactionRepository creditCardTransactionRepository;
    @Autowired
    private UserRepository userRepository;

    public Optional<List<CreditCard>> findByUserId(int id) {
        User user = userService.findById(id);
        return creditCardRepository.findByUser(user);
    }
    //set description in service
    public List<CreditCardTransaction> makeCreditCardPayment(int userId, CreditCardTransactionDTO creditCardTransactionDTO) {
        CreditCard creditCard = creditCardRepository.getById(creditCardTransactionDTO.getCreditCardId());
        Account account = accountRepository.getById(creditCardTransactionDTO.getAccountId());
        User user = userRepository.getById(userId);
        //make sure both account and credit card belong to same user, and credit card belongs to logged in user
        if ((!creditCard.getUser().equals(account.getUser())) || (!creditCard.getUser().equals(user))) {
            //wrong exception
            throw new NotLoggedInException();
        }
        //Make sure account has available balance to make the payment
        if (account.getBalance() < creditCardTransactionDTO.getAmount()) {
           //use new exception that hasn't been pushed as of writing this code
            throw new NotLoggedInException();
        }
        //make sure CC availableBalance does not exceed totallimit after payment
        if (creditCard.getTotalLimit() < (creditCard.getAvailableBalance() + creditCardTransactionDTO.getAmount())) {
            //wrong exception
            throw new NotLoggedInException();
        }

        //now that we have passed all checks, we need to apply/persist the payment to both accounts
        creditCard.setAvailableBalance(creditCard.getAvailableBalance() + creditCardTransactionDTO.getAmount());
        creditCardRepository.save(creditCard);
        account.setBalance((account.getBalance() - creditCardTransactionDTO.getAmount()));
        accountRepository.save(account);

        //Generate a transaction for an account, expense, this will go after validation.
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.EXPENSE);
        transaction.setCreationDate(Date.from(Instant.now()));
        transaction.setAmount(creditCardTransactionDTO.getAmount());
        transaction.setDescription("Credit Card " + creditCardTransactionDTO.getCreditCardId() + " Payment");
        transaction.setAccount(account);
        //toAccountId not required
        transactionRepository.save(transaction);

        //gerenerate payment to cctransaction and save/persist
        CreditCardTransaction creditCardTransaction = new CreditCardTransaction(creditCardTransactionDTO);
        creditCardTransaction.setDescription("Payment from Account " + account.getName());
        creditCardTransaction.setType(CreditCardTransactionType.PAYMENT);
        creditCardTransaction.setCreationDate(Date.from(Instant.now()));
        creditCardTransaction.setCreditCard(creditCard);
        creditCardTransaction.setAccount(account);
        creditCardTransactionRepository.save(creditCardTransaction);

        return creditCardTransactionRepository.findAllByCreditCardOrderByCreationDateDesc(creditCard);

    }
}
