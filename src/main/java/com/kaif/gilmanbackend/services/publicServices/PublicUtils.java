package com.kaif.gilmanbackend.services.publicServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaif.gilmanbackend.exceptions.DuplicateException;
import com.kaif.gilmanbackend.repos.UserRepo;
import com.kaif.gilmanbackend.utilities.Utils;

@Service
public class PublicUtils {

    @Autowired
    private Utils utils;

    @Autowired
    private UserRepo userRepo;

    public void isMobileUniqueAndValid(Long mobile) {
        utils.isValidMobile(mobile);
        var res = userRepo.findByMobile(mobile);
        if (res != null) {
            throw new DuplicateException("Mobile number already registered");
        }
    }

    public void isUsernameUniqueAndValid(String username) {
        utils.isValidUsername(username);
        var res = userRepo.findByUsername(username).isPresent();
        System.out.println(res);
        if (res) {
            throw new DuplicateException("Username already exists");
        }
    }

    public void isEmailUnique(String email) {
        if (email != null) {
            var res = userRepo.findByEmailIgnoreCase(email).isPresent();
            if (res) {
                throw new DuplicateException("Email already exists");
            }
        }

    }

}
