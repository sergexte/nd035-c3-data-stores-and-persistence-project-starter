package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.data.Customer;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.user.customer.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Pet findById(final long petId) {
        return petRepository.getOne(petId);
    }

    public Pet savePet(final Pet pet, long ownerId) {
       final Customer customer = customerRepository.getOne(ownerId);
        pet.setCustomer(customer);
        final Pet savedPet = petRepository.save(pet);

        customer.insertPet(savedPet);
        customerRepository.save(customer);
        return savedPet;
    }

    public List<Pet> findPetsByOwnerId(final long ownerId) {
        return petRepository.findPetsByCustomerId(ownerId);
    }

    public List<Pet> getPets() {
        return petRepository.findAll();
    }
}