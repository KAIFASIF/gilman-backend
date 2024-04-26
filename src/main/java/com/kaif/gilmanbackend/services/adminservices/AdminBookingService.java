package com.kaif.gilmanbackend.services.adminservices;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;

import com.kaif.gilmanbackend.dto.UserAndBookingAndTransaction;
import com.kaif.gilmanbackend.entities.Booking;
import com.kaif.gilmanbackend.repos.BookingRepo;
import com.kaif.gilmanbackend.repos.SlotsRepo;
import com.kaif.gilmanbackend.repos.TransactionRepo;
import com.kaif.gilmanbackend.repos.UserRepo;

@Service
public class AdminBookingService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    // @Autowired
    // private UserBookingAndTransaction userBookingAndTransaction;

    // fetch bookings And User
    public List<UserAndBookingAndTransaction>  fetchBookingsAndUser(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        // Page<Booking> bookingsPage = bookingRepo.findBooking( pageable);
        // bookingRepo.findAll(Sort.by("id").descending(), pageable);

        Booking boo = new Booking();
        boo.setIsPlayed(true);
        // var bookings = bookingsPage.getContent();
        // UserAndBookingAndTransaction payload = new UserAndBookingAndTransaction();
        // var records = bookingRepo.findAll();
        // // var records = bookingRepo.findAllBookingsWithUserMobileAndName(pageable);
        // for (Booking ele : records) {
        // payload.setBooking(ele);
        // payload.setName(ele.getUser().getName());
        // payload.setMobile(ele.getUser().getMobile());

        // }
        // var records = bookingRepo.findAllBookingsWithUserMobileAndName(pageable);
        var records = bookingRepo.findAll(pageable);
        // System.out.println(ele.toString());

        System.out.println("***************************************************");
        List<UserAndBookingAndTransaction> response = new ArrayList<>();
        for (Booking ele : records) {
            var name = ele.getUser().getName();
            var mobile = ele.getUser().getMobile();
            var paymentId = ele.getTransaction().getRazorPayPaymemntId();
            var amountPaid = ele.getTransaction().getAmountPaid();
            response.add(new UserAndBookingAndTransaction(ele, name, mobile, paymentId, amountPaid));
        }
        System.out.println("***************************************************");

        return response;
    }

}
