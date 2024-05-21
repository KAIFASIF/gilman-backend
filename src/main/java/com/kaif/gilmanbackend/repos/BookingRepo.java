package com.kaif.gilmanbackend.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.kaif.gilmanbackend.entities.Booking;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface BookingRepo extends JpaRepository<Booking, Long> {

    @Query("SELECT e FROM Booking e WHERE e.date = :date")
    List<Booking> findByDate(@Param("date") LocalDate date);

    Optional<Booking> findById(Long id);

    Long countByUserId(Long id);

    Page<Booking> findBookingByUserId(Long userId, Pageable pageable);

    @Query("SELECT b, u.name, u.mobile, t.razorPayPaymentId, t.amountPaid FROM Booking b JOIN b.user u JOIN b.transaction t ORDER BY b.id DESC")
    List<Object[]> getBookingsWithUsersAndTransactions(Pageable pageable);

    @Query("SELECT b FROM Booking b JOIN b.user u WHERE u.mobile = :mobile ORDER BY b.id DESC")
    List<Booking> fetchBookingsByMobile(Long mobile, Pageable pageable);

    @Query("SELECT b, t.razorPayPaymentId, t.amountPaid, u.name, u.mobile FROM Booking b JOIN b.transaction t JOIN b.user u WHERE u.mobile = :mobile ORDER BY b.id DESC, t.id DESC")
    List<Object[]> findBookingsAndTransactionsByMobile(Long mobile, Pageable pageable);

    @Query("SELECT COUNT(b) FROM Booking b JOIN b.user u WHERE u.mobile = :mobile")
    int countBookingsByMobile(Long mobile);

    // @Query("SELECT b, t, u FROM Booking b JOIN b.transaction t JOIN b.user u
    // WHERE u.mobile = :mobile ORDER BY b.id DESC, t.id DESC")
    // List<Object[]> findBookingsAndTransactionsByMobile(Long mobile);

    @Query("SELECT e FROM Booking e WHERE  e.date=:date  AND e.startTime >= :startTime AND e.endTime <= :endTime")
    List<Booking> validateBookings(LocalDate date, LocalTime startTime, LocalTime endTime);

    // @Query("SELECT e FROM Booking e WHERE e.date>=:date AND e.startTime >=
    // :startTime AND e.endTime <= :endTime")
    // List<Booking> validateBookings(LocalDate date, LocalTime startTime, LocalTime
    // endTime);

    // @Query("SELECT e FROM Booking e WHERE e.date = :date AND e.startTime >= :time
    // AND e.endTime < :time ")
    // List<Booking> ValidateStartTime(@Param("date") LocalDate date, @Param("time")
    // LocalTime time);

    @Query("SELECT e FROM Booking e WHERE e.date = :date AND  :time >= e.startTime  AND  :time  < e.endTime ")
    List<Booking> validateStartTime(@Param("date") LocalDate date, @Param("time") LocalTime time);

    // List<Booking> validate(@Param("date") LocalDate date, @Param("time")
    // LocalTime startTime, @Param("time") LocalTime endTime);
    
    // @Query("SELECT e FROM Booking e WHERE (e.date = :date AND :startTime >= e.startTime AND :startTime < e.endTime) OR (e.date = :date AND :endTime > e.startTime AND :endTime <= e.endTime)")
    @Query("SELECT e FROM Booking e WHERE (e.date = :date AND :startTime >= e.startTime  AND  :startTime  < e.endTime) OR (e.date = :date AND :endTime > e.startTime  AND  :endTime  <= e.endTime) ")
    List<Booking> validate(@Param("date") LocalDate date, @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime);

    @Query("SELECT e FROM Booking e WHERE e.date = :date AND  :time > e.startTime  AND  :time  <= e.endTime ")
    List<Booking> validateEndTime(@Param("date") LocalDate date, @Param("time") LocalTime time);

    // @Query("SELECT e FROM Booking e WHERE e.date = :date AND :time BETWEEN
    // e.startTime AND e.endTime ")
    // List<Booking> ValidateEndTime(@Param("date") LocalDate date, @Param("time")
    // LocalTime time);

}
