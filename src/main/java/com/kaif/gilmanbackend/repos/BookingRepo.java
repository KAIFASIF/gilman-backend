package com.kaif.gilmanbackend.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kaif.gilmanbackend.entities.Bookings;

import jakarta.persistence.LockModeType;

import java.time.LocalDate;

public interface BookingRepo extends JpaRepository<Bookings, Long> {

    @Query("SELECT e FROM Bookings e WHERE e.date = :date")
    List<Bookings> findByDate(@Param("date") LocalDate date);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT e FROM Bookings e WHERE e.date = :date")
    List<Bookings> findByDateWithLock(@Param("date") LocalDate date);
}
