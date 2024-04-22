package com.kaif.gilmanbackend.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaif.gilmanbackend.entities.Booking;
import java.util.Optional;

public interface BookingRepo extends JpaRepository<Booking, Long> {

    Optional<Booking> findById(Long id);

}
