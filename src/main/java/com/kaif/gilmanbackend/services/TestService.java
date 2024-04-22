package com.kaif.gilmanbackend.services;

import java.time.LocalTime;
import java.util.Optional;
import java.time.LocalDate;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaif.gilmanbackend.entities.Bookings;
import com.kaif.gilmanbackend.entities.Slot;
import com.kaif.gilmanbackend.entities.TransactionsDetails;
import com.kaif.gilmanbackend.entities.User;
import com.kaif.gilmanbackend.exceptions.AlreadyBookedException;
import com.kaif.gilmanbackend.repos.BookingRepo;
import com.kaif.gilmanbackend.repos.SlotRepo;
import com.kaif.gilmanbackend.repos.TransactionsDetailsRepo;
import com.kaif.gilmanbackend.repos.UserRepo;

@Service
public class TestService {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SlotRepo slotRepo;

    @Autowired
    private TransactionsDetailsRepo transactionsDetailsRepo;

    @Transactional
    public void saveBookings(Long userId, String name, String date, LocalTime startTime, LocalTime endTime) {

        User user = userRepo.findById(userId);
        LocalDate parsedDate = LocalDate.parse(date);
        var records = bookingRepo.findBookingsByDateAndTimeInRange(parsedDate, startTime, endTime);
        System.out.println("Ftech :" + records);

        for (Bookings ele : records) {
            if (ele.getIsBooked()) {
                throw new AlreadyBookedException(
                        "Please check the available slots on the slot page for the provided date ");
            }
            ele.setIsBooked(true);
            ele.setName(name);
            ele.setUser(user);
            var res = bookingRepo.save(ele);
        }
        saveSlot(userId, parsedDate, startTime, endTime);

    }

    @Transactional
    public void createBooking1() throws AlreadyBookedException, InterruptedException {
        saveBookings(1L, "Asif", "2024-04-22", LocalTime.parse("06:00"), LocalTime.parse("07:00"));
        Thread.sleep(1000);
    }

    @Transactional
    public void createBooking2() throws AlreadyBookedException, InterruptedException {
        saveBookings(2L, "Kaif", "2024-04-22", LocalTime.parse("06:00"), LocalTime.parse("07:00"));
        Thread.sleep(1000);
    }

    @Transactional
    public void saveSlot(Long userId, LocalDate date, LocalTime starTime, LocalTime endTime) {
        var user = userRepo.findById(userId);
        Slot slot = new Slot();
        slot.setDate(date);
        slot.setStartTime(starTime);
        slot.setEndTime(endTime);
        slot.setUser(user);
        var res = slotRepo.save(slot);
        saveTransaction(user, res.getId());
    }

    @Transactional
    public void saveTransaction(User user, Long id) {
        var slot = slotRepo.findById(id).get();
        TransactionsDetails payemts = new TransactionsDetails();
        payemts.setRazorPayOrdertId("payment_ID");
        payemts.setRazorPayPaymemntId("Order_d");
        payemts.setRazorPaySignature("signatiure");
        payemts.setAmountPaid(4000L);
        payemts.setUser(user);
        payemts.setSlot(slot);
        transactionsDetailsRepo.save(payemts);

        // bookings.setTransactionsDetails(trans);
        // bookingRepo.save(bookings);
    }

    public void resetBookings() {
        var records = bookingRepo.findAll();
        for (Bookings ele : records) {
            ele.setIsBooked(false);
            ele.setName(null);
            ele.setUser(null);
            bookingRepo.save(ele);
        }
    }

}
