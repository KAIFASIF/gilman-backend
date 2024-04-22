package com.kaif.gilmanbackend.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaif.gilmanbackend.entities.Transaction;

import java.util.Optional;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findById(Long id);

}
