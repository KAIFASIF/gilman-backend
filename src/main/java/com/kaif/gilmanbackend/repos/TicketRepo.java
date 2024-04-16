package com.kaif.gilmanbackend.repos;

import org.springframework.data.repository.CrudRepository;

import com.kaif.gilmanbackend.entities.Ticket;

public interface TicketRepo extends CrudRepository<Ticket, Long> {

}