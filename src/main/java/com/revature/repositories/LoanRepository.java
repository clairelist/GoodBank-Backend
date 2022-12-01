package com.revature.repositories;

import com.revature.models.Account;
import com.revature.models.Loan;
import com.revature.models.Transaction;
import com.revature.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {


    List<Loan> findByUser(User user);
}