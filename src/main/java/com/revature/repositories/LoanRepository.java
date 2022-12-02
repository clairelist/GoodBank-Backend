package com.revature.repositories;

import com.revature.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {


    List<Loan> findByUser(User user);

    List<Loan> findByStatus(Status status);
}