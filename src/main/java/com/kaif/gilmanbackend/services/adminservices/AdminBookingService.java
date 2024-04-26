package com.kaif.gilmanbackend.services.adminservices;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public UserAndBookingAndTransaction fetchBookingsAndUser(Integer page, Integer size) {
        // Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        // Page<Booking> bookingsPage = bookingRepo.findBooking( pageable);
        // var bookings = bookingsPage.getContent();
        UserAndBookingAndTransaction payload = new UserAndBookingAndTransaction();
        var records = bookingRepo.findAll();
        // var records = bookingRepo.findAllBookingsWithUserMobileAndName(pageable);
        for (Booking ele : records) {
            payload.setBooking(ele);
            payload.setName(ele.getUser().getName());
            payload.setMobile(ele.getUser().getMobile());
            
        }
        System.out.println("***************************************************");

        System.out.println(records);

        System.out.println("***************************************************");

return payload;
    }

}
