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

import com.rest.webservices.grant.exception.CustomizedIdNotFoundException;
import com.rest.webservices.grant.model.FamilyMember;
import com.rest.webservices.grant.respository.FamilyMemberRespository;

@RestController
public class FamilyMemberController {

	@Autowired
	private FamilyMemberRespository familyMemberRepository;

	// retrieveAllFamilyMember
	@GetMapping("/familymembers")
	public List<FamilyMember> retrieveAllFamilyMembers() {
		return familyMemberRepository.findAll();
	}

	// create FamilyMember and return creation status
	@PostMapping("/familymembers")
	public ResponseEntity<Object> createFamilyMember(@Valid @RequestBody FamilyMember familyMember) {
		familyMemberRepository.save(familyMember);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(familyMember.getId()).toUri();

		// Return creation status [http://localhost:8080/familyMember/4]
		return ResponseEntity.created(location).build();
	}

	@GetMapping("/familymembers/{id}")
	public Optional<FamilyMember> retrieveFamilyMember(@PathVariable int id) {
		Optional<FamilyMember> familyMember = familyMemberRepository.findById(id);
		// check if anything is returned else return customized exception
		if (!(familyMember.isPresent())) {
			//to return message "id-1 not found"
			throw new CustomizedIdNotFoundException("id-" + id);
		}
		return familyMember;
	}

	// deleteFamilyMember
	@DeleteMapping("/familymembers/{id}")
	public void deleteFamilyMember(@PathVariable int id) {
		familyMemberRepository.deleteById(id);
	}
}
