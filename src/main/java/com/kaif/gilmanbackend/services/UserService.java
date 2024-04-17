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
import com.kaif.gilmanbackend.exceptions.NotAuthorized;
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
        payload.setPassword(passwordEncoder.encode(payload.getPassword()));
        userRepo.save(payload);
    }

    public UserResponse signinUser(User payload) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        payload.getUsername(),
                        payload.getPassword()));

        var user = userRepo.findByUsername(payload.getUsername()).orElseThrow();
        String jwt = jwtService.generateToken(user);

        if (!user.getIsAuthorized()) {
            throw new NotAuthorized("You are not authorized to signin, please contact admin");
        }

        revokeAllTokenByUser(user);
        saveUserToken(jwt, user);

        return new UserResponse(user.getId(), user.getName(), user.getRole(), jwt);

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
}
