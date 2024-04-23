package com.kaif.gilmanbackend.utilities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.kaif.gilmanbackend.entities.Booking;


@Service
public class Utils {

    public void isValidName(String name) {
        var namePattern = Pattern.compile("^[A-Za-z ]*$");
        if (name == null || name.trim().isEmpty() || !namePattern.matcher(name.trim()).matches()) {
            throw new IllegalArgumentException("Invalid name");
        }
    }

    public void isValidMobile(Long mobile) {
        var mobilePattern = Pattern.compile("^[6-9]\\d{9}$");
        if (mobile == null || !mobilePattern.matcher(mobile.toString().trim()).matches()) {
            throw new IllegalArgumentException("Invalid mobile");
        }
    }

    public void isValidUsername(String username) {
        var usernamePattern = Pattern.compile("^(?=.*?\\d)(?=.*?[a-zA-Z])[a-zA-Z\\d]+$");
        if (username == null || !usernamePattern.matcher(username.trim()).matches()) {
            throw new IllegalArgumentException("Username must be combination of alpha numeric values");
        }
    }

    public void isValidPassword(String password) {
        var passwordPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
        if (password == null || !passwordPattern.matcher(password.trim()).matches()) {
            throw new IllegalArgumentException(
                    "The password must be at least 8 characters long and contain a combination of alphanumeric characters and special characters.");
        }
    }

    // public void isValidMobileAndName(Bookings payload) {
    //     var namePattern = Pattern.compile("^(?=.*[a-zA-Z])[a-zA-Z0-9 ]*$");
    //     var mobilePattern = Pattern.compile("^[6-9]\\d{9}$");

    //     if (payload.getName() == null || payload.getName().trim().isEmpty()
    //             || !namePattern.matcher(payload.getName().trim()).matches()) {
    //         throw new IllegalArgumentException("Invalid name");
    //     }

    //     if (payload.getMobile() == null || payload.getMobile().toString().isEmpty()
    //             || !mobilePattern.matcher(payload.getMobile().toString().trim()).matches()) {
    //         throw new IllegalArgumentException("Invalid mobile number");
    //     }
    // }

    public Boolean isPastDate(LocalDate payload) {
        LocalDate currentDate = LocalDate.now();
        return payload.isBefore(currentDate);
    }

    public Boolean isTodayDate(LocalDate payload) {
        LocalDate currentDate = LocalDate.now();
        return payload.equals(currentDate);
    }

    public Boolean isFutureDate(LocalDate payload) {
        LocalDate currentDate = LocalDate.now();
        return payload.isAfter(currentDate);
    }

    public Boolean isBookingDatePermitted(LocalDate date) {
        LocalDate currentDate = LocalDate.now();
        Long daysDifference = ChronoUnit.DAYS.between(currentDate, date);
        return daysDifference > 15;
    }

    public Boolean isPastTime(LocalTime payload) {
        LocalTime currentTime = LocalTime.now();
        return payload.isBefore(currentTime);
    }

    public Boolean isTodayAndPastTime(Booking payload) {
        return isPastTime(payload.getStartTime()) && isTodayDate(payload.getDate());
    }

    public Boolean isStartTimeEqualOrInRange(LocalTime payloadStartTime, LocalTime recordStartTime,
            LocalTime recordEndTime) {
        return payloadStartTime.equals(recordStartTime)
                || (payloadStartTime.isAfter(recordStartTime) && payloadStartTime.isBefore(recordEndTime));
    }

    public Boolean isEndTimeEqualOrInRange(LocalTime payloadEndTime, LocalTime recordStartTime,
            LocalTime recordEndTime) {
        return payloadEndTime.equals(recordEndTime)
                || (payloadEndTime.isAfter(recordStartTime) && payloadEndTime.isBefore(recordEndTime));
    }

    public Boolean isStartTimeAllowed(LocalTime startTime) {
        LocalTime rangeStart = LocalTime.of(22, 59);
        LocalTime rangeEnd = LocalTime.of(6, 0);
        return (rangeStart.isBefore(rangeEnd) && startTime.isAfter(rangeStart) && startTime.isBefore(rangeEnd))
                || (rangeStart.isAfter(rangeEnd) && (startTime.isAfter(rangeStart) || startTime.isBefore(rangeEnd)));
    }

    public Boolean isEndTimeAllowed(LocalTime endTime) {
        LocalTime rangeStart = LocalTime.of(23, 0);
        LocalTime rangeEnd = LocalTime.of(6, 01);
        return (endTime.isBefore(rangeEnd) && endTime.isAfter(rangeStart) && endTime.isBefore(rangeEnd))
                || (rangeStart.isAfter(rangeEnd) && (endTime.isAfter(rangeStart) || endTime.isBefore(rangeEnd)));
    }

    public void isDateAndTimeValid(Booking payload) {

        if (isPastDate(payload.getDate())) {
            throw new IllegalArgumentException("Past date bookings is not allowed");
        }

        if (isBookingDatePermitted(payload.getDate())) {
            throw new IllegalArgumentException("Slot cannot be booked the more than 15 days");
        }

        if (payload.getStartTime().equals(payload.getEndTime())) {
            throw new IllegalArgumentException("start time and end time cannnot be same");
        }

        if (payload.getStartTime().isAfter(payload.getEndTime())) {
            throw new IllegalArgumentException("start time cannnot be greater than end time");
        }

        if (isStartTimeAllowed(payload.getStartTime()) || isEndTimeAllowed(payload.getEndTime())) {
            throw new IllegalArgumentException("Cannot book between 11 pm to 6:00 am");
        }

        if (isTodayAndPastTime(payload)) {
            throw new IllegalArgumentException("Booking is not allowed as time is already passed");
        }

    }

}
