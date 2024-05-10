package com.kaif.gilmanbackend.services;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
// import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import com.kaif.gilmanbackend.utilities.Utils;

import jakarta.transaction.Transactional;
// import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class TestService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private Utils utils;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private SlotsRepo slotRepo;

    @Transactional
    public void saveTransaction(User user, Long id) {
        var booking = bookingRepo.findById(id).get();
        // Transaction payments = new Transaction(user, booking);
        // payemts.setRazorPayOrdertId("Order_Id");
        // payemts.setRazorPayPaymemntId("payment_ID");
        // payemts.setRazorPaySignature("signatiure");
        // payemts.setAmountPaid(4000L);
        // payemts.setUser(user);
        // payemts.setBoo(slot);
        // transactionRepo.save(payments);

    }

    @Transactional
    public void saveBooking(Long userId, Long amount, LocalDate date, LocalTime startTime, LocalTime endTime) {
        var user = userRepo.findById(userId).get();
        var hours = utils.calculateTime(startTime, endTime);
        Booking payload = new Booking(date, startTime, endTime, user, "Box Cricket", hours);
        var res = bookingRepo.save(payload);
        saveTransaction(user, res.getId());

    }

    // @Transactional
    // public JSONObject createOrder(Long amount) throws RazorpayException {
    // String keyId = "rzp_test_gSK9TTIhMBYv7S";
    // String secretkey = "iVz21pLz35iW7fMRIj7kmmTd";
    // var razorpayClient = new RazorpayClient(keyId, secretkey);
    // JSONObject options = new JSONObject();
    // options.put("amount", amount * 100);
    // options.put("currency", "INR");
    // var orderResponse = razorpayClient.orders.create(options);
    // JSONObject jsonResponse = new JSONObject(orderResponse.toString());
    // return jsonResponse;
    // }

    @Transactional
    public void validateBoooking(Long userId, Long amount, LocalDate date, LocalTime startTime, LocalTime endTime)
            // public JSONObject validateBoooking(Long amount, LocalDate date, LocalTime
            // startTime, LocalTime endTime)
            throws RazorpayException {

        var records = slotRepo.findBookingsByDateAndTimeInRange(date, startTime, endTime);
        if (records.size() == 0) {
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
        saveBooking(userId, amount, date, startTime, endTime);
        // var jsonResponse = createOrder(amount);
        // return jsonResponse;
    }

    @Transactional
    public void validateBoookingAndcreateOrder(Long userId, Long amount, LocalDate date, LocalTime startTime,
            LocalTime endTime)
            // public JSONObject validateBoookingAndcreateOrder(Long userId, Long amount,
            // Booking payload)
            throws AlreadyBookedException, InterruptedException, RazorpayException {
        validateBoooking(userId, amount, date, startTime, endTime);
        // return jsonResponse;
    }

    @Transactional
    public void method1() throws AlreadyBookedException, InterruptedException, RazorpayException {
        validateBoookingAndcreateOrder(1L, 400L, LocalDate.parse("2024-04-24"), LocalTime.parse("06:00"),
                LocalTime.parse("07:00"));
        Thread.sleep(1000);
    }

    @Transactional
    public void method2() throws AlreadyBookedException, InterruptedException, RazorpayException {
        validateBoookingAndcreateOrder(21L, 2000L, LocalDate.parse("2024-04-24"), LocalTime.parse("06:00"),
                LocalTime.parse("07:00"));
        Thread.sleep(1000);
    }

}
