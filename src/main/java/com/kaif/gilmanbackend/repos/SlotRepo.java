package com.kaif.gilmanbackend.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaif.gilmanbackend.entities.Slot;

public interface SlotRepo extends JpaRepository<Slot, Long> {

}
