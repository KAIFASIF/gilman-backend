package com.kaif.gilmanbackend.services.adminservices;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.kaif.gilmanbackend.entities.Booking;
import com.kaif.gilmanbackend.entities.Transaction;
import com.kaif.gilmanbackend.entities.User;
import com.kaif.gilmanbackend.repos.BookingRepo;
import com.kaif.gilmanbackend.repos.UserRepo;

@Service
public class AdminBookingService {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private UserRepo userRepo;

    // fetch bookings
    public Map<String, Object> fetchBookingsUserAndTransaction(Integer page, Integer size) {
        var count = bookingRepo.count();

        Pageable pageable = PageRequest.of(page, size);
        List<Object[]> bookingUserTransactionList = bookingRepo.getBookingsWithUsersAndTransactions(pageable);
        List<Map<String, Object>> bookingDetailsList = bookingUserTransactionList.stream()
                .map(ele -> {
                    Booking booking = (Booking) ele[0];
                    String name = (String) ele[1];
                    Long mobile = (Long) ele[2];
                    String paymentId = (String) ele[3];
                    Long amountPaid = (Long) ele[4];

                    Map<String, Object> bookingDetails = new HashMap<>();
                    bookingDetails.put("booking", booking);
                    bookingDetails.put("name", name);
                    bookingDetails.put("mobile", mobile);
                    bookingDetails.put("paymentId", paymentId);
                    bookingDetails.put("amountPaid", amountPaid);

                    return bookingDetails;
                })
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("bookings", bookingDetailsList);
        result.put("count", count);

        return result;
    }

    // fetch bookings, by mobile in paginated manner
    public Map<String, Object> fetchBookingsByMobile(Long mobile, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        var bookingUserTransactionList = bookingRepo.findBookingsAndTransactionsByMobile(mobile, pageable);

      
        var count = bookingRepo.countBookingsByMobile(mobile);

        List<Map<String, Object>> bookingDetailsList = bookingUserTransactionList.stream()
                .map(ele -> {
                    Booking booking = (Booking) ele[0];
                    String paymentId = (String) ele[1];
                    Long amountPaid = (Long) ele[2];
                    String name = (String) ele[3];
                    Map<String, Object> bookingDetails = new HashMap<>();
                    bookingDetails.put("booking", booking);
                    bookingDetails.put("paymentId", paymentId);
                    bookingDetails.put("amountPaid", amountPaid);
                    bookingDetails.put("name", name);
                    bookingDetails.put("mobile", mobile);

                    return bookingDetails;
                })
                .collect(Collectors.toList());
        Map<String, Object> result = new HashMap<>();
        result.put("bookings", bookingDetailsList);
        result.put("count", count);
        return result;

    }

}
