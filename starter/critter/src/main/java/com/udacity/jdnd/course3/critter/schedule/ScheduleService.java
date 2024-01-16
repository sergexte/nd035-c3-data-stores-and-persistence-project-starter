package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.data.Customer;
import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.data.Schedule;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.customer.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleService {

    @Autowired private ScheduleRepository scheduleRepository;

    @Autowired private EmployeeRepository employeeRepository;

    @Autowired private CustomerRepository customerRepository;

    @Autowired private PetRepository petRepository;

    public Schedule saveSchedule(Schedule schedule, List<Long> employeeIds, List<Long> petIds) {
        final List<Employee> employees = filterEmployeesWithIds(employeeIds);
        final List<Pet> pets = filterPetsWithIds(petIds);

        schedule.setEmployees(employees);
        schedule.setPets(pets);

        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForEmployee(final long employeeId) {
        return scheduleRepository.findByEmployeeId(employeeId);
    }

    public List<Schedule> getScheduleForPet(final long petId) {
        return scheduleRepository.findByPetId(petId);
    }

    public List<Schedule> getScheduleForCustomer(final long customerId) {
        final Customer customer = customerRepository.getOne(customerId);
        final List<Long> petIds =
                customer.getPets().stream().map(Pet::getId).collect(Collectors.toList());

        return scheduleRepository.findByPetIds(petIds);
    }

    private List<Employee> filterEmployeesWithIds(List<Long> employeeIds) {
        return employeeRepository.findAll().stream()
                .filter(employee -> employeeIds.contains(employee.getId()))
                .collect(Collectors.toList());
    }

    private List<Pet> filterPetsWithIds(List<Long> petIds) {
        return petRepository.findAll().stream()
                .filter(pet -> petIds.contains(pet.getId()))
                .collect(Collectors.toList());
    }
}
