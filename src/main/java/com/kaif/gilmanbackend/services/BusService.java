package com.kaif.gilmanbackend.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaif.gilmanbackend.entities.BusDetails;
import com.kaif.gilmanbackend.entities.Ticket;
import com.kaif.gilmanbackend.exceptions.ResourceNotFound;
import com.kaif.gilmanbackend.exceptions.SeatNotAvailable;
import com.kaif.gilmanbackend.repos.BusRepo;
import com.kaif.gilmanbackend.repos.TicketRepo;

import jakarta.transaction.Transactional;

@Service
public class BusService {

    @Autowired
    private BusRepo busRepo;

    @Autowired
    private TicketRepo ticketRepo;

    private void saveTicket(String firstName, String lastName, String gender, BusDetails busDetails)
            throws SeatNotAvailable {

        if (busDetails.getCapacity() <= busDetails.getTickets().size()) {
            // throw new SeatNotAvailable();
            throw new ResourceNotFound("Seat not available");
        }

        Ticket ticket = new Ticket();
        ticket.setFirstName(firstName);
        ticket.setLastName(lastName);
        ticket.setGender(gender);

        busDetails.addTicket(ticket);

        ticketRepo.save(ticket);
    }

    @Transactional
    public void bookTicket() throws SeatNotAvailable, InterruptedException {
        Optional<BusDetails> busOptional = busRepo.findWithLockingById(1L);

        if (busOptional.isPresent()) {
            saveTicket("Asif", "Asif", "Male", busOptional.get());
        }
    }

    @Transactional
    public void bookTicket1() throws SeatNotAvailable, InterruptedException {
        Optional<BusDetails> busOptional = busRepo.findWithLockingById(1L);

        if (busOptional.isPresent()) {
            saveTicket("Asif", "Asif", "Male", busOptional.get());
        }

        Thread.sleep(1000);
    }

    @Transactional
    public void bookTicket2() throws SeatNotAvailable, InterruptedException {
        Optional<BusDetails> busOptional = busRepo.findWithLockingById(1L);

        if (busOptional.isPresent()) {
            saveTicket("Kaif", "Kaif", "Male", busOptional.get());
        }

        Thread.sleep(1000);
    }
}