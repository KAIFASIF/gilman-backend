package com.kaif.gilmanbackend.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kaif.gilmanbackend.entities.Bookings;

import jakarta.persistence.LockModeType;

import java.time.LocalDate;
import java.time.LocalTime;

public interface BookingRepo extends JpaRepository<Bookings, Long> {

    @Query("SELECT e FROM Bookings e WHERE e.date = :date")
    List<Bookings> findByDate(@Param("date") LocalDate date);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT e FROM Bookings e WHERE e.date = :date")
    List<Bookings> findByDateWithLock(@Param("date") LocalDate date);

    @Query("SELECT e FROM Bookings e WHERE  e.date>=:date AND e.date=:date AND e.startTime >= :startTime AND e.endTime <= :endTime")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Bookings> findBookingsByDateAndTimeInRange(LocalDate date, LocalTime startTime, LocalTime endTime);

    Optional<Bookings> findById(Long id);
}