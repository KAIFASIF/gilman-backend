package com.kaif.gilmanbackend.services.userServices;

import java.util.Map;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.kaif.gilmanbackend.dto.BookingAndTransactionRequest;
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
public class UserBookingService {

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

    // Save booking and transaction
    @Transactional
    public void saveTransaction(User user, Booking bookingPayload, Transaction transaction) {
        Transaction payload = new Transaction(user, bookingPayload, transaction.getBookingAmount() / 100,
                transaction.getAmountPaid() / 100, transaction.getRazorPayPaymentId(),
                transaction.getRazorPayOrdertId(), transaction.getRazorPaySignature());

        var res = transactionRepo.save(payload);
        bookingPayload.setTransaction(res);
        bookingRepo.save(bookingPayload);

    }

    @Transactional
    public void saveBooking(User user, Booking booking, Transaction transaction) {
        Booking payload = new Booking(booking.getDate(), booking.getStartTime(), booking.getEndTime(), user,
                booking.getSport());
        var bookingResponse = bookingRepo.save(payload);
        saveTransaction(user, bookingResponse, transaction);
    }

    // Below method are being called from another end points
    @Transactional
    public void createBookingAndTransaction(Long userId, BookingAndTransactionRequest payload) {
        var user = userRepo.findById(userId).get();
        saveBooking(user, payload.getBooking(), payload.getTransaction());
    }

    // fetch User bookings
    public Map<String, Object> fetchUserBookings(Long userId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        var count = bookingRepo.countByUserId(userId);
        var bookingList = bookingRepo.findBookingByUserId(userId, pageable);

        Map<String, Object> result = new HashMap<>();
        result.put("booking", bookingList.getContent());
        result.put("count", count);

        return result;
    }

}
