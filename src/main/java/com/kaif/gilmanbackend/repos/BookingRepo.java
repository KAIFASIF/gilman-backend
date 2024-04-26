package com.kaif.gilmanbackend.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kaif.gilmanbackend.entities.Booking;
import com.kaif.gilmanbackend.entities.User;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.util.Optional;

public interface BookingRepo extends JpaRepository<Booking, Long> {

    @Query("SELECT e FROM Booking e WHERE e.date = :date")
    List<Booking> findByDate(@Param("date") LocalDate date);

    Optional<Booking> findById(Long id);
    Page<Booking> findBookingByUserId(Long userId, Pageable pageable);

    // @Query("SELECT b, u FROM Booking b JOIN FETCH b.user u")
    // Page<Booking> findBookings(Pageable pageable);

    // @Query("SELECT b FROM Booking b JOIN FETCH b.user u ORDER BY b.id DESC")
    //  @Query("SELECT FROM User  u AND  Booking  b WHERE u.id = b.user_id") // iinener join
    @Query("SELECT b, u.mobile As mobile  FROM Booking b JOIN b.user u ORDER BY b.id DESC")
    List<Map<String, Object>> findAllBookingsWithUserMobileAndNamess(Pageable pageable);

//     // @Query("SELECT b, u.mobile  FROM Booking b JOIN b.user u ORDER BY b.id DESC")
//     @Query("SELECT b, NEW map(u.name AS name, u.mobile As mobile) FROM Booking b JOIN b.user u ORDER BY b.id DESC")
//     List<Map<String, Object>> getBookingsss();

// // save
//     @Query("SELECT b, u.mobile  FROM Booking b JOIN b.user u ORDER BY b.id DESC")
//     List<Object[]> findAllBookingsWithUserMobileAndName(Pageable pageable);
  
    // @Query("SELECT u.name FROM User u RIGHT JOIN Booking  b WHERE user.id= b.user.id")
    // List<Object[]> getBookingANDUser();

    // @Query("select s.id, s.sport from Booking s")
    // List<Object[]> getSchoolIdAndName();


    // @Query("SELECT NEW map(b.id AS id,b.sport AS sport) FROM Booking b")
    // @Query("SELECT b, u.mobile as mobile, u.name as name FROM Booking b JOIN b.user u ORDER BY b.id DESC")
    // List<Map<String, Object>> findAllBookingsWithUserMobileAndName(Pageable pageable);

    // @Query("SELECT NEW map(b.id AS id,b.sport AS sport) FROM Booking b")
    // List<Map<String, Object>> getBookings();

    // @Query("SELECT NEW map(b.id AS id,b.sport AS sport) FROM Booking b JOIN b.user u")
    // @Query("SELECT NEW map(u.name AS name, u.mobile As mobile) FROM User u RIGHT JOIN u.booking b")
    // List<Map<String, Object>> getBookingss();

    // @Query("SELECT b, u.mobile, u.name FROM Booking b JOIN b.user u ORDER BY b.id DESC")
    // List<Map<String, Object>> getBookings();
}
