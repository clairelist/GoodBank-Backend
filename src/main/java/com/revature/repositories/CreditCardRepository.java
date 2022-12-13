package com.revature.repositories;

import com.revature.models.CreditCard;
import com.revature.models.Status;
import com.revature.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {
    List<CreditCard> findByUser(User user);

    List<CreditCard> findByStatus(Status status);

}
