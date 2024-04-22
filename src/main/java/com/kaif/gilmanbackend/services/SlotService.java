package com.kaif.gilmanbackend.services;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaif.gilmanbackend.entities.Slots;
import com.kaif.gilmanbackend.exceptions.AlreadyBookedException;
import com.kaif.gilmanbackend.repos.SlotsRepo;

import jakarta.transaction.Transactional;

@Service
public class SlotService {

    @Autowired
    private SlotsRepo slotRepo;

    @Autowired
    private BookingService bookingService;

    @Transactional
    public void saveSlot(Long userId, String name, String date, LocalTime startTime, LocalTime endTime) {

        LocalDate parsedDate = LocalDate.parse(date);
        var records = slotRepo.findBookingsByDateAndTimeInRange(parsedDate, startTime, endTime);
        
        for (Slots ele : records) {
            if (ele.getIsBooked()) {
                throw new AlreadyBookedException(
                        "Please check the available slots on the slot page for the provided date ");
            }
            ele.setIsBooked(true);
            slotRepo.save(ele);
        }
        bookingService.saveBooking(userId, parsedDate, startTime, endTime);

    }

    @Transactional
    public void createBooking1() throws AlreadyBookedException, InterruptedException {
        saveSlot(1L, "Asif", "2024-04-22", LocalTime.parse("06:00"), LocalTime.parse("07:00"));
        Thread.sleep(1000);
    }

    @Transactional
    public void createBooking2() throws AlreadyBookedException, InterruptedException {
        saveSlot(2L, "Kaif", "2024-04-23", LocalTime.parse("06:00"), LocalTime.parse("07:00"));
        Thread.sleep(1000);
    }

}
