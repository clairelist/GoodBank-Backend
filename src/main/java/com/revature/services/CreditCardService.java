package com.revature.services;

import com.revature.dtos.CreditCardDTO;
import com.revature.dtos.CreditCardTransactionDTO;
import com.revature.dtos.NotificationCreationRequest;
import com.revature.dtos.UserDTO;
import com.revature.exceptions.*;
import com.revature.models.NotificationType;
import com.revature.models.*;
import com.revature.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CreditCardService {

    private UserService userService;
    private CreditCardRepository creditCardRepository;
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private CreditCardTransactionRepository creditCardTransactionRepository;
    private UserRepository userRepository;
    private NotificationService ns;
    private TokenService tokenService;
    private Random rand;

    @Autowired
    public CreditCardService(
            UserService userService,
            CreditCardRepository creditCardRepository,
            AccountRepository accountRepository,
            TransactionRepository transactionRepository,
            CreditCardTransactionRepository creditCardTransactionRepository,
            UserRepository userRepository,
            TokenService tokenService,
            NotificationService ns) {
        this.userService = userService;
        this.creditCardRepository = creditCardRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.creditCardTransactionRepository = creditCardTransactionRepository;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.ns = ns;
    }

    public List<CreditCard> findByUserId(int id) {
        User user = userService.findById(id);
        return creditCardRepository.findByUser(user);
    }
    //set description in service
    public Double makeCreditCardPayment(int userId, CreditCardTransactionDTO creditCardTransactionDTO) {
        CreditCard creditCard = creditCardRepository.getById(creditCardTransactionDTO.getCreditCardId());
        Account account = accountRepository.getById(creditCardTransactionDTO.getAccountId());
        User user = userRepository.getById(userId);
        //make sure both account and credit card belong to same user, and credit card belongs to logged in user
        if ((!creditCard.getUser().equals(account.getUser())) || (!creditCard.getUser().equals(user))) {
            //wrong exception
            throw new InvalidUserException();
        }
        //Make sure account has available balance to make the payment
        if (account.getBalance() < creditCardTransactionDTO.getAmount()) {
            throw new InsufficientFundsException();
        }
        //make sure CC availableBalance does not exceed totallimit after payment
        if (creditCard.getTotalLimit() < (creditCard.getAvailableBalance() + creditCardTransactionDTO.getAmount())) {
            //wrong exception
            throw new ExceedsTotalLimitException();
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

        //generate payment to cctransaction and save/persist
        CreditCardTransaction creditCardTransaction = new CreditCardTransaction(creditCardTransactionDTO);
        creditCardTransaction.setDescription("Payment from Account " + account.getName());
        creditCardTransaction.setType(CreditCardTransactionType.PAYMENT);
        creditCardTransaction.setCreationDate(Date.from(Instant.now()));
        creditCardTransaction.setCreditCard(creditCard);
        creditCardTransaction.setAccount(account);
        creditCardTransactionRepository.save(creditCardTransaction);

        return creditCard.getAvailableBalance();

    }

    public CreditCard createCCApplication(String userId, double totalLimit) {
        CreditCard newCC = new CreditCard();
        try {
            rand = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new NoAlgException();
        }
        if(totalLimit <= 0){
            throw new InvalidAmountException();
        }
        if(String.valueOf(totalLimit).trim().equals("")) {
            throw new InvalidInputException();
        }
        UserDTO currentUser = tokenService.extractTokenDetails(userId);
        User user = userRepository.getById(currentUser.getId());
        newCC.setTotalLimit(totalLimit);
        newCC.setUser(user);
        newCC.setAvailableBalance(totalLimit);
        newCC.setCardNumber((long) (rand.nextDouble() * 1000000000000000L));
        newCC.setCcv(rand.nextInt((9999 - 100) + 1) + 10);
        newCC.setStatus(Status.PENDING);
        Instant date = Instant.now();
        Instant expiration = date.plus(740, ChronoUnit.DAYS);
        newCC.setExpirationDate(Date.from(expiration));
        creditCardRepository.save(newCC);

        NotificationCreationRequest notif = new NotificationCreationRequest();
        notif.setUser(user);
        notif.setType(NotificationType.INFORMATION);
        notif.setBody("Thanks for applying for a credit card with us! We've got some Good™ news, you're credit card application is being processed!");
        ns.create(notif);

        return newCC;
    }

    public List<CreditCardTransaction> getTransactionsByCreditCardId(int creditCardId, String userId) {
        UserDTO currentUser = tokenService.extractTokenDetails(userId);
        User user = userRepository.getById(currentUser.getId());
        CreditCard creditCard = creditCardRepository.getById(creditCardId);
        if(!creditCard.getUser().equals(user)) {
            throw new AuthorizationException();
        }
        return creditCardTransactionRepository.findAllByCreditCardOrderByCreationDateDesc(creditCard);
    }

    public List<CreditCard> getPendingCreditCards(String userId) {
        UserDTO currentUser = tokenService.extractTokenDetails(userId);
        User user = userRepository.getById(currentUser.getId());
        if(!user.getUserType().equals(UserType.ADMIN)) {
            throw new AuthorizationException();
        }
        return creditCardRepository.findByStatus(Status.PENDING);
    }

    public CreditCard updateCreditCardStatus(String userId, CreditCardDTO creditCard) {
        UserDTO currentUser = tokenService.extractTokenDetails(userId);
        User user = userRepository.getById(currentUser.getId());
        if(!user.getUserType().equals(UserType.ADMIN)) {
            throw new AuthorizationException();
        }
        CreditCard updatedCreditCard = creditCardRepository.getById(creditCard.getId());
        updatedCreditCard.setStatus(creditCard.getStatus());
        if (creditCard.getStatus().equals(Status.DENIED)) {
            creditCardRepository.delete(updatedCreditCard);
            return null;
        }
        return creditCardRepository.save(updatedCreditCard);
    }
}
