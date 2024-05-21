package com.kaif.gilmanbackend.services.testServices;

import org.antlr.v4.runtime.atn.SemanticContext.AND;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaif.gilmanbackend.entities.Booking;
import com.kaif.gilmanbackend.exceptions.AlreadyBookedException;
import com.kaif.gilmanbackend.repos.BookingRepo;
import com.kaif.gilmanbackend.repos.TestMultiThreadingrepo;
import com.razorpay.RazorpayException;
import java.time.LocalTime;
import java.time.LocalDate;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.UUID;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Service
public class TestMultiThreadService {

    @Autowired
    private BookingRepo bookingRepo;

    // public synchronized void synchronizedSave(LocalDate date, LocalTime
    // startTime,
    // LocalTime endTime) {
    // var count = bookingRepo.validate(date, startTime, endTime).size();
    // if (count > 0) {
    // throw new AlreadyBookedException(
    // "Please check the available slots on the slot page for the provided date ");
    // }
    // var payload = new Booking();
    // payload.setDate(date);
    // payload.setStartTime(startTime);
    // payload.setEndTime(endTime);
    // bookingRepo.save(payload);
    // }

    // public void method1() throws AlreadyBookedException, InterruptedException,
    // RazorpayException {
    // synchronizedSave(LocalDate.parse("2024-05-25"), LocalTime.parse("04:00"),
    // LocalTime.parse("05:00"));
    // }

    // public void method2() throws AlreadyBookedException, InterruptedException,
    // RazorpayException {
    // synchronizedSave(LocalDate.parse("2024-05-25"), LocalTime.parse("04:00"),
    // LocalTime.parse("05:00"));
    // }

    static ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();

    public void traverse() {
        System.out.println("******************************************");
        System.out.println(map);
        System.out.println("******************************************");

    };

    public void save(LocalDate date, LocalTime startTime, LocalTime endTime) {
        var count = bookingRepo.validate(date, startTime, endTime).size();
        if (count > 0) {
            throw new AlreadyBookedException(
                    "Please check the available slots on the slot page for the provided date ");
        }
        var payload = new Booking();
        payload.setDate(date);
        payload.setStartTime(startTime);
        payload.setEndTime(endTime);
        bookingRepo.save(payload);
    }

    public Boolean validate(LocalDate givenDate, LocalTime givenStartTime, LocalTime givenEndTime) {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            String[] data = entry.getValue().split(" , ");
            LocalDate date = LocalDate.parse(data[0]);
            LocalTime startTime = LocalTime.parse(data[1]);
            LocalTime endTime = LocalTime.parse(data[2]);

            if ((date.equals(givenDate) && givenStartTime.equals(startTime)
                    || (date.equals(givenDate) && givenStartTime.isAfter(startTime)
                            && givenStartTime.isBefore(endTime)))
                    || (date.equals(givenDate) && givenEndTime.equals(endTime)
                            || (date.equals(givenDate) && givenEndTime.isAfter(startTime)
                                    && givenEndTime.isBefore(endTime)))) {
                return true;
            }
        }
        return false;

    }

    public void isTimeWithinRange(LocalDate date, LocalTime startTime, LocalTime endTime) {
        String data = date.toString() + " , " + startTime.toString() + " , " + endTime.toString();
        if (!validate(date, startTime, endTime)) {
            map.put(map.size() + 1, data);
            save(date, startTime, endTime);
        }
    }

    // public void lockFreeSave(LocalDate date, LocalTime starTime, LocalTime
    // endTime) {

    // var date1 = LocalDate.parse("2024-05-26");
    // var startTime1 = LocalTime.parse("01:00");
    // var endTime1 = LocalTime.parse("05:30");

    // String data1 = LocalDate.parse("2024-05-25") + " , " +
    // LocalTime.parse("01:00").toString() + " , "
    // + LocalTime.parse("04:00").toString();
    // String data2 = LocalDate.parse("2024-05-25") + " , " +
    // LocalTime.parse("05:00").toString() + " , "
    // + LocalTime.parse("08:00").toString();

    // cMap.put("key1", data1);
    // cMap.put("key2", data2);

    // System.out.println("*****************************************");
    // System.out.println(isTimeWithinRange(date, startTime, endTime));
    // System.out.println("*****************************************");

    // }

    public void method1() throws AlreadyBookedException, InterruptedException, RazorpayException {
        isTimeWithinRange(LocalDate.parse("2024-05-29"), LocalTime.parse("01:00"), LocalTime.parse("04:00"));
        Thread.sleep(1000);
    }

    public void method2() throws AlreadyBookedException, InterruptedException, RazorpayException {
        isTimeWithinRange(LocalDate.parse("2024-05-28"), LocalTime.parse("04:00"), LocalTime.parse("08:00"));
        Thread.sleep(1000);
    }


    public void testMethod()  {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
       
        // ExecutorService executor = Executors.newFixedThreadPool(2);
        int corePoolSize = availableProcessors;
        int maxPoolSize = availableProcessors * 2;
        int queueCapacity = 100;

       
        try {
             ExecutorService executor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueCapacity),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );


        executor.submit(() -> {
            isTimeWithinRange(LocalDate.parse("2024-05-31"), LocalTime.parse("01:00"), LocalTime.parse("04:00"));
        });
        executor.submit(() -> {
            isTimeWithinRange(LocalDate.parse("2024-05-31"), LocalTime.parse("04:00"), LocalTime.parse("05:00"));
        });
        executor.shutdown();
        } catch (Exception e) {
            
        }
       
    }

}
