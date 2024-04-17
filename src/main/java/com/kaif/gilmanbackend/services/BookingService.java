package com.kaif.gilmanbackend.services;

import java.util.List;
import java.time.LocalTime;
import java.time.LocalDate;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.kaif.gilmanbackend.entities.Bookings;
import com.kaif.gilmanbackend.entities.TransactionsDetails;
import com.kaif.gilmanbackend.exceptions.ResourceNotFoundException;
import com.kaif.gilmanbackend.repos.BookingRepo;
import com.kaif.gilmanbackend.repos.TransactionsDetailsRepo;
import com.kaif.gilmanbackend.utilities.Utils;

@Service
public class BookingService {

    @Autowired
    private Utils utils;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private TransactionsDetailsRepo transactionsDetailsRepo;

    @Transactional
    public List<Bookings> fetchSlots(LocalDate date) {
        return bookingRepo.findByDate(date);
    }

    @Transactional
    public void createTestBooking(Bookings payload) {
        LocalTime payloadStartTime = payload.getStartTime();
        LocalTime payloadEndTime = payload.getEndTime();
        var records = bookingRepo.findByDateWithLock(payload.getDate());
        for (Bookings ele : records) {
            if (utils.isStartTimeEqualOrInRange(payloadStartTime, ele.getStartTime(), ele.getEndTime())
                    || utils.isEndTimeEqualOrInRange(payloadEndTime, ele.getStartTime(), ele.getEndTime())) {
                throw new ResourceNotFoundException(
                        "Please check the available slots  on the slot page for the provided date");
            }
        }

        saveTransactions(payload);
        bookingRepo.save(payload);
    }

    @Transactional
    public void saveTransactions(Bookings payload) { 
        var details = new TransactionsDetails();
        details.setAmountPaid(500L);
        details.setTransactionId("ghghghgh155");
        details.setMobile(9700174021L);        
        transactionsDetailsRepo.save(details);
    }

}
