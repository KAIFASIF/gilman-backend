package com.kaif.gilmanbackend.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaif.gilmanbackend.entities.TransactionsDetails;

public interface TransactionsDetailsRepo extends JpaRepository<TransactionsDetails, Long> {

}
