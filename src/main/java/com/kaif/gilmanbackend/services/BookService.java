package com.kaif.gilmanbackend.services;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaif.gilmanbackend.entities.Bookings;
import com.kaif.gilmanbackend.exceptions.ResourceNotFound;
import com.kaif.gilmanbackend.repos.BookingRepo;
import com.kaif.gilmanbackend.utilities.Utils;

@Service
public class BookService {

    @Autowired
    private Utils utils;

    @Autowired
    private BookingRepo bookingRepo;

    public void createBooking(Bookings payload) {
        LocalTime payloadStartTime = payload.getStartTime();
        LocalTime payloadEndTime = payload.getEndTime();
        var records = bookingRepo.findByDate(payload.getDate());

        for (Bookings ele : records) {

            if (utils.isStartTimeEqualOrInRange(payloadStartTime, ele.getStartTime(), ele.getEndTime())) {
                throw new ResourceNotFound("Ground is already booked for given date and time");
            }

            if (utils.isEndTimeEqualOrInRange(payloadEndTime, ele.getStartTime(), ele.getEndTime())) {
                throw new ResourceNotFound("Ground is already booked for given date and time");
            }
        }

        bookingRepo.save(payload);

    }

}
