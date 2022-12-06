package com.revature.repositories;

import com.revature.models.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionPageRepository extends PagingAndSortingRepository<Transaction, Integer> {

    List<Transaction> findAllByAccountId(int accountId, Pageable pageable);
}
