package com.kaif.gilmanbackend.services;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kaif.gilmanbackend.entities.Booking;
import com.kaif.gilmanbackend.entities.Slots;
import com.kaif.gilmanbackend.entities.Transaction;
import com.kaif.gilmanbackend.entities.User;
import com.kaif.gilmanbackend.exceptions.AlreadyBookedException;
import com.kaif.gilmanbackend.exceptions.ResourceNotFoundException;
import com.kaif.gilmanbackend.repos.BookingRepo;
import com.kaif.gilmanbackend.repos.SlotsRepo;
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

    @Autowired
    private SlotsRepo slotRepo;

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

    @Transactional
    public void saveBooking(Long userId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        var user = userRepo.findById(userId).get();
        Booking payload = new Booking(date, startTime, endTime, user);
        var res = bookingRepo.save(payload);
        saveTransaction(user, res.getId());
    }

    @Transactional
    public void createBooking(Long userId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        var records = slotRepo.findBookingsByDateAndTimeInRange(date, startTime, endTime);
        // System.out.println("************************************************************");
        // System.out.println(records);
        // System.out.println("************************************************************");
        if (records.size() == 0) {
            throw new ResourceNotFoundException("Booking not allowed for given date and time");
        }
        for (Slots ele : records) {
            if (ele.getIsBooked()) {
                throw new AlreadyBookedException(
                        "Please check the available slots on the slot page for the provided date ");
            }
            ele.setIsBooked(true);
            slotRepo.save(ele);
        }
        saveBooking(userId, date, startTime, endTime);
    }

    @Transactional
    public void createOrder(Long userId, Booking payload) throws AlreadyBookedException, InterruptedException {
        createBooking(userId, payload.getDate(), payload.getStartTime(), payload.getEndTime());
    }

    // @Transactional
    // public void method1() throws AlreadyBookedException, InterruptedException {
    // createBooking(1L, LocalDate.parse("2024-04-24"), LocalTime.parse("06:00"),
    // LocalTime.parse("07:00"));
    // Thread.sleep(1000);
    // }

    // @Transactional
    // public void method2() throws AlreadyBookedException, InterruptedException {
    // createBooking(2L, LocalDate.parse("2024-04-24"), LocalTime.parse("06:00"),
    // LocalTime.parse("07:00"));
    // Thread.sleep(1000);
    // }

}
