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
import com.kaif.gilmanbackend.repos.BookingRepo;

@Service
public class AdminBookingService {

    @Autowired
    private BookingRepo bookingRepo;

    // fetch bookings
    public Map<String, Object> fetchBookingsUserAndTransaction(Integer page, Integer size) {
        var count = bookingRepo.count();

        Pageable pageable = PageRequest.of(page, size);
        List<Object[]> bookingUserTransactionList = bookingRepo.getBookingsWithUsersAndTransactions(pageable);
        List<Map<String, Object>> bookingDetailsList = bookingUserTransactionList.stream()
                .map(ele -> {
                    Booking booking = (Booking) ele[0];
                    String userName = (String) ele[1];
                    Long userMobile = (Long) ele[2];
                    String paymentId = (String) ele[3];
                    Long amountPaid = (Long) ele[4];

                    Map<String, Object> bookingDetails = new HashMap<>();
                    bookingDetails.put("booking", booking);
                    bookingDetails.put("name", userName);
                    bookingDetails.put("mobile", userMobile);
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

}
