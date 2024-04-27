package com.kaif.gilmanbackend.services.publicServices;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaif.gilmanbackend.entities.Booking;
import com.kaif.gilmanbackend.repos.BookingRepo;

@Service
public class PublicBookedSlotService {

    @Autowired
    private BookingRepo bookingRepo;

    public List<Booking> fetchBookedSlots(LocalDate date) {
        return bookingRepo.findByDate(date);

    }

}
