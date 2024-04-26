package com.kaif.gilmanbackend.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;

import com.kaif.gilmanbackend.dto.BookingAndTransactionRequest;
import com.kaif.gilmanbackend.dto.UserAndBookingAndTransaction;
import com.kaif.gilmanbackend.entities.Booking;
import com.kaif.gilmanbackend.entities.Slots;
import com.kaif.gilmanbackend.entities.Transaction;
import com.kaif.gilmanbackend.entities.User;
import com.kaif.gilmanbackend.exceptions.AlreadyBookedException;
import com.kaif.gilmanbackend.exceptions.ResourceNotFoundException;
import com.kaif.gilmanbackend.repos.BookingRepo;
import com.kaif.gilmanbackend.repos.SlotsRepo;
import com.kaif.gilmanbackend.repos.TransactionRepo;
import com.kaif.gilmanbackend.repos.UserRepo;

@Service
public class BookingService {

    @Autowired
    private SlotsRepo slotRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.secret.key}")
    private String secretKey;

    @Transactional
    public void resetLockedRows(Booking payload) {
        var records = slotRepo.findBookingsByDateAndTimeInRange(payload.getDate(), payload.getStartTime(),
                payload.getEndTime());
        if (!records.isEmpty()) {
            for (Slots ele : records) {
                ele.setIsBooked(false);
                slotRepo.save(ele);
            }
        }
    }

    @Transactional
    public JSONObject createOrder(Long amount) throws RazorpayException {
        var razorpayClient = new RazorpayClient(keyId, secretKey);
        JSONObject options = new JSONObject();
        options.put("amount", amount * 100);
        options.put("currency", "INR");
        var orderResponse = razorpayClient.orders.create(options);
        JSONObject jsonResponse = new JSONObject(orderResponse.toString());
        return jsonResponse;
    }

    @Transactional
    public JSONObject validateBoooking(Long amount, LocalDate date, LocalTime startTime, LocalTime endTime)
            throws RazorpayException {
        var records = slotRepo.findBookingsByDateAndTimeInRange(date, startTime,
                endTime);
        if (records.isEmpty()) {
            throw new ResourceNotFoundException("Booking not allowed for given date and time");
        }
        for (Slots ele : records) {
            if (ele.getIsBooked()) {
                throw new AlreadyBookedException(
                        "Please check the available slots on the slot page for the provided date ");
            }
            ele.setIsBooked(true);
            slotRepo.save(ele);
        }
        var jsonResponse = createOrder(amount);
        return jsonResponse;
    }

    @Transactional
    public JSONObject validateBoookingAndcreateOrder(Long amount, Booking payload)
            throws AlreadyBookedException, InterruptedException, RazorpayException {
        var jsonResponse = validateBoooking(amount, payload.getDate(), payload.getStartTime(), payload.getEndTime());
        return jsonResponse;
    }

    // Below methods are being called from another end points
    @Transactional
    public void saveTransaction(User user, Booking booking, Transaction transaction) {
        Transaction payload = new Transaction(user, booking, transaction.getBookingAmount() / 100,
                transaction.getAmountPaid() / 100, transaction.getRazorPayPaymemntId(),
                transaction.getRazorPayOrdertId(), transaction.getRazorPaySignature());

        transactionRepo.save(payload);
    }

    @Transactional
    public void saveBooking(User user, Booking booking, Transaction transaction) {
        System.out.println(booking);
        Booking payload = new Booking(booking.getDate(), booking.getStartTime(), booking.getEndTime(), user,
                booking.getSport());
        var response = bookingRepo.save(payload);
        saveTransaction(user, response, transaction);
    }

    @Transactional
    public void createBookingAndTransaction(Long userId, BookingAndTransactionRequest payload) {
        var user = userRepo.findById(userId).get();
        saveBooking(user, payload.getBooking(), payload.getTransaction());
    }

    // fetch bookings
    public List<Booking> fetchBookings(Long userId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<Booking> bookingsPage = bookingRepo.findBookingByUserId(userId, pageable);
        var bookings = bookingsPage.getContent();

        return bookings;

    }

}
