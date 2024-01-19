package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.data.Schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s JOIN s.employees e WHERE e.id = :employeeId")
    List<Schedule> findByEmployeeId(@Param("employeeId") Long employeeId);

    @Query("SELECT s FROM Schedule s JOIN s.pets p WHERE p.id = :petId")
    List<Schedule> findByPetId(@Param("petId") Long petId);

    @Query("SELECT s FROM Schedule s JOIN s.pets p WHERE p.id IN :petIds")
    List<Schedule> findByPetIds(@Param("petIds") List<Long> petIds);
}
