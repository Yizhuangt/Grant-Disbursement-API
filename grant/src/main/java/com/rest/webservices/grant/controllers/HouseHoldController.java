package com.rest.webservices.grant.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rest.webservices.grant.model.HouseHold;
import com.rest.webservices.grant.respository.HouseHoldRespository;

//Controller which can handle rest request
@RestController
public class HouseHoldController {

	@Autowired
	private HouseHoldRespository houseHoldRepository;

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
		return household;
	}

	// deleteFamilyMember
	@DeleteMapping("/households/{id}")
	public void deleteHouseHold(@PathVariable int id) {
		houseHoldRepository.deleteById(id);
	}
}
