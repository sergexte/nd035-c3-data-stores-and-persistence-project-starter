package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.data.Customer;
import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.user.customer.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.customer.CustomerService;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * <p>Includes requests for both customers and employees. Splitting this into separate user and
 * customer controllers would be fine too, though that is not part of the required scope for this
 * class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired private CustomerService customerService;

    @Autowired private EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        final Customer customer = customerService.saveCustomer(convertToEntity(customerDTO), customerDTO.getPetIds());
        return convertToDTO(customer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        final Customer customer = customerService.getOwnerByPet(petId);
        return convertToDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        final Employee employee = employeeService.saveEmployee(convertToEntity(employeeDTO));
        return convertToDTO(employee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return employeeService.getEmployee(employeeId).map(this::convertToDTO).orElse(null);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(
            @RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        return employeeService
                .findEmployeesForService(employeeDTO.getSkills(), employeeDTO.getDate())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @RequestMapping("/error")
    public String handleError() {
        return "error";
    }

    private Customer convertToEntity(CustomerDTO customerDTO) {
        final Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    private CustomerDTO convertToDTO(Customer customer) {
        final CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);

        if (customer.getPets() != null) {
            final List<Long> petIds = customer.getPets().stream().map(Pet::getId).collect(Collectors.toList());
            customerDTO.setPetIds(petIds);
        }

        return customerDTO;
    }

    private Employee convertToEntity(EmployeeDTO employeeDTO) {
        final Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employee.setAvailability(employeeDTO.getDaysAvailable());
        return employee;
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        final EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        employeeDTO.setDaysAvailable(employee.getAvailability());
        return employeeDTO;
    }
}
