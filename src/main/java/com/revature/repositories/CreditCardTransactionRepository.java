package com.revature.repositories;

import com.revature.models.CreditCard;
import com.revature.models.CreditCardTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardTransactionRepository extends JpaRepository<CreditCardTransaction, Integer> {
    List<CreditCardTransaction> findAllByCreditCardOrderByCreationDateDesc(CreditCard creditCard);
}
