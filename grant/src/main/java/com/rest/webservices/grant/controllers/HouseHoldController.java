package com.rest.webservices.grant.controllers;

import java.net.URI;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rest.webservices.grant.exception.CustomizedIdNotFoundException;
import com.rest.webservices.grant.model.FamilyMember;
import com.rest.webservices.grant.model.HouseHold;
import com.rest.webservices.grant.respository.FamilyMemberRespository;
import com.rest.webservices.grant.respository.HouseHoldRespository;

//Controller which can handle rest request
@RestController
public class HouseHoldController {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(HouseHoldController.class);

	@Autowired
	private HouseHoldRespository houseHoldRepository;

	@Autowired
	private FamilyMemberRespository familyMemberRepository;

	@GetMapping("/households")
	public List<HouseHold> retrieveAllHouseHold() {
		return houseHoldRepository.findAll();
	}

	// 1. Create a household
	@PostMapping(path = "/households")
	public ResponseEntity<Object> createHouseHold(@Valid @RequestBody HouseHold household) {
		houseHoldRepository.save(household);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(household.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	// get household with id
	@GetMapping("/households/{id}")
	public Optional<HouseHold> retrieveHouseHoldById(@PathVariable int id) {
		Optional<HouseHold> household = houseHoldRepository.findById(id);

		if (!(household.isPresent())) {
			throw new CustomizedIdNotFoundException("id-" + id);
		}
		return household;
	}

	// delete household with id
	@DeleteMapping("households/{id}")
	public void deleteHouseHold(@PathVariable int id) {
		// Check if household can be found
		Optional<HouseHold> householdOptional = houseHoldRepository.findById(id);
		if (!(householdOptional.isPresent())) {
			throw new CustomizedIdNotFoundException("id-" + id);
		}

		// get all the family members in household
		List<FamilyMember> familyMember = householdOptional.get().getFamilyMembers();

		// delete all familyMembers in household
		for (FamilyMember fm : familyMember) {
			familyMemberRepository.deleteById(fm.getId());
		}

		// delete the household
		houseHoldRepository.deleteById(id);
	}

	// get family members in a household
	@GetMapping(path = "/households/{id}/familymembers")
	public List<FamilyMember> retrieveAllFamilyMemebers(@PathVariable int id) {
		Optional<HouseHold> householdOptional = houseHoldRepository.findById(id);

		if (!(householdOptional.isPresent())) {
			throw new CustomizedIdNotFoundException("id-" + id);
		}

		return householdOptional.get().getFamilyMembers();
	}

	// create family member to a household
	@PostMapping(path = "/households/{id}/familymembers")
	public ResponseEntity<Object> createFamilyMemberForHouseHold(@PathVariable int id,
			@RequestBody FamilyMember familyMember) {
		Optional<HouseHold> householdOptional = houseHoldRepository.findById(id);
		// Throw exception if the household does not exist
		if (!(householdOptional.isPresent())) {
			throw new CustomizedIdNotFoundException("id-" + id);
		}

		HouseHold household = householdOptional.get();
		// set household into familyMember and save it
		familyMember.setHouseHold(household);
		familyMemberRepository.save(familyMember);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(familyMember.getId()).toUri();

		// return the URI of the created post
		return ResponseEntity.created(location).build();

	}

	// deleteFamilyMember from household
	@DeleteMapping("/households/{id}/familymembers/{fmid}")
	public void deleteFamilyMember(@PathVariable int id, @PathVariable int fmid) {
		Optional<FamilyMember> familyMemberOptional = familyMemberRepository.findById(fmid);

		if (!(familyMemberOptional.isPresent())) {
			throw new CustomizedIdNotFoundException("id 1-" + id);
		}

		if (familyMemberOptional.get().getHouseHold().getId() == id) {
			familyMemberRepository.deleteById(fmid);
		} else {
			throw new CustomizedIdNotFoundException("id 2-" + id);
		}
	}
}
