package com.kaif.gilmanbackend.repos;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.kaif.gilmanbackend.entities.TestMultiThreadingClass;

public interface TestMultiThreadingrepo extends JpaRepository<TestMultiThreadingClass, Long> {

    @Query("SELECT e FROM TestMultiThreadingClass e WHERE  e.date>=:date AND e.date=:date AND e.startTime >= :startTime AND e.endTime <= :endTime")
    List<TestMultiThreadingClass> findTestMultiThreadingClassByDateAndTimeInRange(LocalDate date, LocalTime startTime,
            LocalTime endTime);

}
