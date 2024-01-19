package com.udacity.jdnd.course3.critter.user.employee;

import com.udacity.jdnd.course3.critter.data.Employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    @Autowired private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Optional<Employee> getEmployee(long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        final Employee employee = employeeRepository.getOne(employeeId);
        employee.setAvailability(daysAvailable);
        employeeRepository.save(employee);
    }

    public List<Employee> findEmployeesForService(Set<EmployeeSkill> skills, LocalDate date) {
        final List<Employee> employees =
                employeeRepository.getAllByAvailabilityContains(date.getDayOfWeek());
        return employees.stream()
                .filter(employee -> employee.getSkills().containsAll(skills))
                .collect(Collectors.toList());
    }
}
