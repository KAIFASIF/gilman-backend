package com.kaif.gilmanbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang3.function.FailableRunnable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;
import com.kaif.gilmanbackend.entities.Bookings;
import com.kaif.gilmanbackend.repos.BookingRepo;
import com.kaif.gilmanbackend.services.TestService;

@RestController
public class TestController {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private TestService testService;

    @PostMapping("/addScheduleBookings")
    public void insertRecords() {
        List<Bookings> payloads = new ArrayList<>();
        var tomorrowDate =LocalDate.now().plusDays(1);
        payloads.add(new Bookings(LocalDate.now(), LocalTime.parse("06:00"), LocalTime.parse("06:30")));
        payloads.add(new Bookings(LocalDate.now(), LocalTime.parse("06:30"), LocalTime.parse("07:00")));
        payloads.add(new Bookings(LocalDate.now(), LocalTime.parse("07:00"), LocalTime.parse("07:30")));
        payloads.add(new Bookings(LocalDate.now(), LocalTime.parse("07:30"), LocalTime.parse("08:00")));
        payloads.add(new Bookings(LocalDate.now(), LocalTime.parse("08:00"), LocalTime.parse("08:30")));
        payloads.add(new Bookings(LocalDate.now(), LocalTime.parse("08:30"), LocalTime.parse("09:00")));
        payloads.add(new Bookings(LocalDate.now(), LocalTime.parse("09:00"), LocalTime.parse("09:30")));
        payloads.add(new Bookings(LocalDate.now(), LocalTime.parse("09:30"), LocalTime.parse("10:00")));
        payloads.add(new Bookings(LocalDate.now(), LocalTime.parse("10:00"), LocalTime.parse("10:30")));
        payloads.add(new Bookings(LocalDate.now(), LocalTime.parse("10:30"), LocalTime.parse("11:00")));

        payloads.add(new Bookings(tomorrowDate, LocalTime.parse("06:00"), LocalTime.parse("06:30")));
        payloads.add(new Bookings(tomorrowDate, LocalTime.parse("06:30"), LocalTime.parse("07:00")));
        payloads.add(new Bookings(tomorrowDate, LocalTime.parse("07:00"), LocalTime.parse("07:30")));
        payloads.add(new Bookings(tomorrowDate, LocalTime.parse("07:30"), LocalTime.parse("08:00")));
        payloads.add(new Bookings(tomorrowDate, LocalTime.parse("08:00"), LocalTime.parse("08:30")));
        payloads.add(new Bookings(tomorrowDate, LocalTime.parse("08:30"), LocalTime.parse("09:00")));
        payloads.add(new Bookings(tomorrowDate, LocalTime.parse("09:00"), LocalTime.parse("09:30")));
        payloads.add(new Bookings(tomorrowDate, LocalTime.parse("09:30"), LocalTime.parse("10:00")));
        payloads.add(new Bookings(tomorrowDate, LocalTime.parse("10:00"), LocalTime.parse("10:30")));
        payloads.add(new Bookings(tomorrowDate, LocalTime.parse("10:30"), LocalTime.parse("11:00")));
        bookingRepo.saveAll(payloads);
    }

    // @PostMapping("/book-slot")
    // public ResponseEntity<?> test(@RequestBody Bookings payload) {
    //     try {
    //         // utils.isValidMobileAndName(payload);
    //         utils.isDateAndTimeValid(payload);
    //         testService.createTestBooking(payload);
    //         return ResponseEntity.status(HttpStatus.CREATED).body("Slot booked sucessfully");
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
    //     }
    // }

    @PostMapping("/test-slot")
	public ResponseEntity<?> bookTicket() {
        try {
            ExecutorService executor = Executors.newFixedThreadPool(2);
            executor.execute(run(testService::createBooking1));
            executor.execute(run(testService::createBooking2));
            executor.shutdown();
        // testService.resetBookings();

            return ResponseEntity.status(HttpStatus.CREATED).body("booked sucessfulluy");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
		
	}

	private Runnable run(FailableRunnable<Exception> runnable) {
		return () -> {
			try {
				runnable.run();
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	}

}
