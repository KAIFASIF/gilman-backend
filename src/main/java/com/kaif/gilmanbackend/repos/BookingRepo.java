package com.kaif.gilmanbackend.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    Page<Booking> findBookingByUserId(Long userId, Pageable pageable);

    // @Query("SELECT b, u FROM Booking b JOIN FETCH b.user u")
    // Page<Booking> findBookings(Pageable pageable);

    @Query("SELECT b, u.mobile, u.name FROM Booking b JOIN b.user u ORDER BY b.id DESC")
    Page<Object[]> findAllBookingsWithUserMobileAndName(Pageable pageable);

    

}
