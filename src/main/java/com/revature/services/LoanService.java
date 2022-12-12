package com.revature.services;

import com.revature.dtos.LoanDTO;
import com.revature.dtos.LoanDetails;
import com.revature.dtos.NotificationCreationRequest;
import com.revature.dtos.UserDTO;
import com.revature.exceptions.AppliedLoanException;
import com.revature.exceptions.AppliedLoanPasswordException;
import com.revature.exceptions.LoansNotFoundException;
import com.revature.exceptions.UserNotAllowedException;
import com.revature.models.*;
import com.revature.repositories.LoanRepository;
import com.revature.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.revature.models.UserType.ADMIN;

@Service
public class LoanService {



    private UserRepository ur;

    private UserService us;

    private LoanRepository lr;
    private TokenService ts;

    private final NotificationService ns;
    @Autowired
    public LoanService(UserRepository ur, UserService us, LoanRepository lr, TokenService ts, NotificationService ns) {
        this.ur = ur;
        this.us = us;
        this.lr = lr;
        this.ts = ts;
        this.ns = ns;
    }

    public LoanDetails createLoan(LoanDTO appliedLoan, int userId) {
        Loan newLoan = new Loan();
        User user = ur.getById(userId);
        
        if (appliedLoan.getInitialAmount() < 0 || appliedLoan.getReason().equals("")){
            throw new AppliedLoanException();
        }
        else if(!Objects.equals(appliedLoan.getPassword(), user.getPassword())){
            throw new AppliedLoanPasswordException();
        }
        newLoan.setInitialAmount(appliedLoan.getInitialAmount());
        newLoan.setReason(appliedLoan.getReason());
        newLoan.setCreationDate(Date.from(Instant.now()));
        newLoan.setBalance(appliedLoan.getInitialAmount());
        newLoan.setUser(user);
        newLoan.setStatus(Status.PENDING);
        Loan savedLoan = lr.save(newLoan);

        // TODO make sure to create corresponding transaction on account?

        NotificationCreationRequest notif = new NotificationCreationRequest();
        notif.setUser(user);
        notif.setType(NotificationType.LOAN);
        notif.setReferencesId(savedLoan.getId());
        DateFormat formatter = new SimpleDateFormat("E, dd MMMM yyyy h:mm:ss aa");
        String strDate = formatter.format(savedLoan.getCreationDate());
        DecimalFormat format2 = new DecimalFormat("#,###.##");

        notif.setBody("A loan application for $" + format2.format(savedLoan.getInitialAmount()) + " was successfully created on " + strDate + ". Keep an eye out for the result!");
        ns.create(notif);


        return new LoanDetails(newLoan);

    }

    public List<Loan> getUserLoans(int userId) {
        User user = us.findById(userId);

        return lr.findByUser(user);
    }

    public List<LoanDetails> getPendingLoans(String userType){
        UserDTO currentUser = ts.extractTokenDetails(userType);
        String realType = currentUser.getType().toString();
        List<LoanDetails> results = new ArrayList<>();
        if(Objects.equals(realType, ADMIN.toString())){
            return lr.findByStatus(Status.PENDING).stream().map(x -> new LoanDetails(x)).collect(Collectors.toList());
        }
        if (results.isEmpty()){
            throw new LoansNotFoundException();
        }else {
            return results;
        }
    }

    public LoanDetails updateLoanStatus(String userType, LoanDetails updateLoan) {
        UserDTO currentUser = ts.extractTokenDetails(userType);
        String realType = currentUser.getType().toString();
        Loan loan = lr.getById(updateLoan.getLoanID());
        if (!Objects.equals(realType, ADMIN.toString())){
            throw new UserNotAllowedException();
        } else {
            loan.setStatus(Status.valueOf(updateLoan.getStatus()));
            Loan savedLoan = lr.save(loan);
            User user = ur.getById(loan.getUser().getId());
            NotificationCreationRequest notif = new NotificationCreationRequest();
            notif.setUser(user);
            notif.setType(NotificationType.LOAN);
            notif.setReferencesId(savedLoan.getId());
            DateFormat formatter = new SimpleDateFormat("E, dd MMMM yyyy h:mm:ss aa");
            String strDate = formatter.format(savedLoan.getCreationDate());
            DecimalFormat format2 = new DecimalFormat("#,###.##");

            notif.setBody("Your loan for $" + format2.format(savedLoan.getInitialAmount()) + " was " + savedLoan.getStatus() + " on " + strDate + ".");
            ns.create(notif);

            return updateLoan;
        }
    }
}
