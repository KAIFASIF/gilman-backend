package com.kaif.gilmanbackend.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaif.gilmanbackend.entities.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
    Optional<User> findByMobile(Long mobile);
}
