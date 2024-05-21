package com.kaif.gilmanbackend.services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaif.gilmanbackend.entities.TestMultiThreadingClass;
import com.kaif.gilmanbackend.repos.TestMultiThreadingrepo;

@Service
public class MulthreadService {

    @Autowired
    private TestMultiThreadingrepo repo;

    private Map<Long, Boolean> userLocks = new ConcurrentHashMap<>();

    public String saveUser(String name, Long userId) {

        if (userLocks.putIfAbsent(userId, true) != null) {
            return "Another request is processing. Please try again later";
        }

        try {
            System.out.println("Success in service" + userId);
            // TestMultiThreadingClass payload = new TestMultiThreadingClass();
            // payload.setName(name);
            // payload.setUserId(userId);
            // repo.save(payload);
            // Simulating some processing time
            Thread.sleep(5000);
            return "User saved successfully";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "An error occurred while saving user";
        } finally {
            userLocks.remove(userId);
        }
    }

    public String testMultiThread1() {
        return saveUser("a", 6L);
    }

    public String testMultiThread2() {
        return saveUser("b", 6L);
    }
    public String testMultiThread3() {
        return saveUser("c", 7L);
    }
    public String testMultiThread4() {
        return saveUser("d", 8L);
    }
    public String testMultiThread5() {
        return saveUser("e", 9L);
    }

}
