package com.kaif.gilmanbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kaif.gilmanbackend.config.JwtService;
import com.kaif.gilmanbackend.dto.UserResponse;
import com.kaif.gilmanbackend.entities.Token;
import com.kaif.gilmanbackend.entities.User;
import com.kaif.gilmanbackend.exceptions.DuplicateException;
import com.kaif.gilmanbackend.exceptions.NotAuthorizedException;
import com.kaif.gilmanbackend.exceptions.ResourceNotFoundException;
import com.kaif.gilmanbackend.repos.TokenRepo;
import com.kaif.gilmanbackend.repos.UserRepo;
import com.kaif.gilmanbackend.utilities.Utils;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private Utils utils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TokenRepo tokenRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    public void saveUser(User payload) {
        validateUser(payload);
        payload.setPassword(passwordEncoder.encode(payload.getPassword()));
        userRepo.save(payload);
    }

    public UserResponse signinUser(User payload) {
        var mobile = payload.getMobile();
        utils.isValidMobile(mobile);
        utils.isValidPassword(payload.getPassword());
        var user = userRepo.findByMobile(mobile);
        if (user == null) {
            throw new ResourceNotFoundException("Invalid credential");
        }
        if (!user.getIsAuthorized()) {
            throw new NotAuthorizedException("You are not authorized to signin, please contact admin");
        }

        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), payload.getPassword()));

        String jwt = jwtService.generateToken(user);
        revokeAllTokenByUser(user);
        saveUserToken(jwt, user);
        return new UserResponse(user.getId(), user.getName(), user.getRole(), user.getMobile(), jwt);
    }

    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepo.findAllTokensByUser(user.getId());
        if (validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t -> {
            t.setLoggedOut(true);
        });

        tokenRepo.saveAll(validTokens);
    }

    private void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepo.save(token);
    }

    public void validateUser(User payload) {
        utils.isValidPassword(payload.getPassword());
        utils.isValidName(payload.getName());
        isMobileUniqueAndValid(payload.getMobile());
        isUsernameUniqueAndValid(payload.getUsername());
        isEmailUnique(payload.getEmail());
    }

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
