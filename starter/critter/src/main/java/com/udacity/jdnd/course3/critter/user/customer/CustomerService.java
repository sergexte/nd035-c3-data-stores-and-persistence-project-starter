package com.udacity.jdnd.course3.critter.user.customer;

import com.udacity.jdnd.course3.critter.data.Customer;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    public Customer saveCustomer(Customer customer, List<Long> petIds) {
        if (petIds != null) {
            final List<Pet> pets = petIds.stream().map(id -> petRepository.getOne(id)).collect(Collectors.toList());
            customer.setPets(pets);
        }
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getOwnerByPet(long petId) {
        return petRepository.getOne(petId).getCustomer();
    }
}
