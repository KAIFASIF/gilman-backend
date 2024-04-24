package com.kaif.gilmanbackend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.kaif.gilmanbackend.entities.User;
import com.kaif.gilmanbackend.repos.TokenRepo;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private final String SECRET_KEY = "4bb6d1dfbafb64a681139d1586b6f1160d18159afd57c8c79136d7490630407c";

    @Autowired
    private TokenRepo tokenRepository;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isValid(String token, UserDetails user) {
        String username = extractUsername(token);

        boolean validToken = tokenRepository
                .findByToken(token)
                .map(t -> !t.isLoggedOut())
                .orElse(false);

        return (username.equals(user.getUsername())) && !isTokenExpired(token) && validToken;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    // private Claims extractAllClaims(String token) {
    // return Jwts
    // .parser()
    // .verifyWith(getSigninKey())
    // .build()
    // .parseSignedClaims(token)
    // .getPayload();
    // }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // public String generateToken(User user) {
    // String token = Jwts
    // .builder()
    // .subject(user.getUsername())
    // .issuedAt(new Date(System.currentTimeMillis()))
    // .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
    // .signWith(getSigninKey())
    // .compact();

    // return token;
    // }

    public String generateToken(User user) {
        String token = Jwts.builder()
                .setSubject(user.getUsername()) // Use setSubject() instead of subject()
                .setIssuedAt(new Date(System.currentTimeMillis())) // Use setIssuedAt() instead of issuedAt()
                // .setExpiration(new Date(System.currentTimeMillis() +   60 * 1000)) // Use setExpiration()
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) // Use setExpiration()
                                                                                           // instead of expiration()
                .signWith(getSigninKey())
                .compact();

        return token;
    }

    // private SecretKey getSigninKey() {
    //     byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
    //     return Keys.hmacShaKeyFor(keyBytes);
    // }

    private SecretKey getSigninKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
    

}
