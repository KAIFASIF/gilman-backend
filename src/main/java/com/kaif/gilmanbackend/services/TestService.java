package com.kaif.gilmanbackend.services;

import java.time.LocalTime;
import java.time.LocalDate;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaif.gilmanbackend.entities.Bookings;
import com.kaif.gilmanbackend.exceptions.AlreadyBookedException;
import com.kaif.gilmanbackend.repos.BookingRepo;

@Service
public class TestService {

    @Autowired
    private BookingRepo bookingRepo;

    @Transactional
    public void saveBookings(String name, String date, LocalTime startTime, LocalTime endTime) {
        LocalDate dated = LocalDate.parse(date);
        var records = bookingRepo.findBookingsByDateAndTimeInRange(dated, startTime,
                endTime);
        System.out.println("Ftech :" + records);

        for (Bookings ele : records) {
            if (ele.getIsBooked()) {
                throw new AlreadyBookedException(
                        "Please check the available slots on the slot page for the provided date ");
            }
            ele.setIsBooked(true);
            ele.setName(name);
            bookingRepo.save(ele);
        }

    }

    @Transactional
    public void createBooking1() throws AlreadyBookedException, InterruptedException {
        saveBookings("Asif", "2024-04-22", LocalTime.parse("06:00"), LocalTime.parse("07:00"));
        Thread.sleep(1000);
    }

    @Transactional
    public void createBooking2() throws AlreadyBookedException, InterruptedException {
        saveBookings("Kaif", "2024-04-23", LocalTime.parse("06:00"), LocalTime.parse("07:00"));
        Thread.sleep(1000);
    }

    public void resetBookings() {
        var records = bookingRepo.findAll();
        for (Bookings ele : records) {
            ele.setIsBooked(false);
            ele.setName(null);
            bookingRepo.save(ele);
        }
    }

}


