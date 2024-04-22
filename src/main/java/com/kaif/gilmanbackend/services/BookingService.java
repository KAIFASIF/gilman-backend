package com.kaif.gilmanbackend.services;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kaif.gilmanbackend.entities.Booking;
import com.kaif.gilmanbackend.entities.Transaction;
import com.kaif.gilmanbackend.entities.User;
import com.kaif.gilmanbackend.repos.BookingRepo;
import com.kaif.gilmanbackend.repos.TransactionRepo;
import com.kaif.gilmanbackend.repos.UserRepo;
import jakarta.transaction.Transactional;

@Service
public class BookingService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    // @Autowired
    // private Transaction transaction;

    @Transactional
    public void saveBooking(Long userId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        var user = userRepo.findById(userId).get();
        Booking payload = new Booking(date, startTime, endTime, user);
        var res = bookingRepo.save(payload);
        saveTransaction(user, res.getId());
    }

    @Transactional
    public void saveTransaction(User user, Long id) {
        var booking = bookingRepo.findById(id).get();
        Transaction payments = new Transaction(user, booking);
        // payemts.setRazorPayOrdertId("Order_Id");
        // payemts.setRazorPayPaymemntId("payment_ID");
        // payemts.setRazorPaySignature("signatiure");
        // payemts.setAmountPaid(4000L);
        // payemts.setUser(user);
        // payemts.setBoo(slot);
        transactionRepo.save(payments);

    }

}
