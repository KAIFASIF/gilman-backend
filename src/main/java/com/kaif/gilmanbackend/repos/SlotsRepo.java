package com.kaif.gilmanbackend.repos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import jakarta.persistence.LockModeType;

import com.kaif.gilmanbackend.entities.Slots;

public interface SlotsRepo extends JpaRepository<Slots, Long> {

    @Query("SELECT e FROM Slots e WHERE  e.date>=:date AND e.date=:date AND e.startTime >= :startTime AND e.endTime <= :endTime")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Slots> findBookingsByDateAndTimeInRange(LocalDate date, LocalTime startTime, LocalTime endTime);


    default void unlockRow() {}

}
