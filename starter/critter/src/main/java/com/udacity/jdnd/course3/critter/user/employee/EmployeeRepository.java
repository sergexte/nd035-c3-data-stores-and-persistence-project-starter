package com.udacity.jdnd.course3.critter.user.employee;

import com.udacity.jdnd.course3.critter.data.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> getAllByAvailabilityContains(DayOfWeek dayOfWeek);
}
