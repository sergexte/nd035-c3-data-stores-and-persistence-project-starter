package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.data.Pet;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/** Handles web requests related to Pets. */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired private PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        final Pet pet = petService.savePet(convertToEntity(petDTO), petDTO.getOwnerId());
        return convertToDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertToDTO(petService.findById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets() {
        return petService.getPets().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return petService.findPetsByOwnerId(ownerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private PetDTO convertToDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getCustomer().getId());
        return petDTO;
    }

    private Pet convertToEntity(PetDTO petDTO) {
        final Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }
}
