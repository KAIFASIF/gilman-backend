package com.kaif.gilmanbackend.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kaif.gilmanbackend.entities.Booking;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

public interface BookingRepo extends JpaRepository<Booking, Long> {

    @Query("SELECT e FROM Booking e WHERE e.date = :date")
    List<Booking> findByDate(@Param("date") LocalDate date);


    Optional<Booking> findById(Long id);

}
