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

	// seb = Student Encouragement Bonus
	// Children less than 16 years old
	// income less than $150,000
	@GetMapping("/seb")
	public List<HouseHold> retrieveSEBHouseHold() {
		List<HouseHold> householdList = houseHoldRepository.findAll();

		Iterator<HouseHold> iterator = householdList.iterator();
		while (iterator.hasNext()) {
			List<FamilyMember> familyMemberList = iterator.next().getFamilyMembers();
			int totalIncome = calculateIncome(familyMemberList);
			boolean hasAgeBelow16 = belowAge(familyMemberList, 16);

			if ((totalIncome >= 150000) || (hasAgeBelow16 == false)) {
				iterator.remove();
				continue;
			}
		}

		return householdList;
	}

	// fts - Family Together Scheme
	// household with husband & wife
	// children younger than 18
	@GetMapping("/fts")
	public List<HouseHold> retrieveFTSHouseHold() {
		List<HouseHold> householdList = houseHoldRepository.findAll();

		Iterator<HouseHold> iterator = householdList.iterator();
		while (iterator.hasNext()) {
			List<FamilyMember> familyMemberList = iterator.next().getFamilyMembers();
			boolean hasBelowAge18 = belowAge(familyMemberList, 18);
			boolean hasSpouse = hasSpouse(familyMemberList);

			if ((hasSpouse == false) || (hasBelowAge18 == false)) {
				iterator.remove();
				continue;
			}
		}
		return householdList;
	}

	// eb = Elder Bonus
	// family member aged above 50
	@GetMapping("/eb")
	public List<HouseHold> retrieveEBHouseHold() {
		List<HouseHold> householdList = houseHoldRepository.findAll();

		Iterator<HouseHold> iterator = householdList.iterator();
		while (iterator.hasNext()) {
			List<FamilyMember> familyMemberList = iterator.next().getFamilyMembers();
			boolean hasAboveAge50 = aboveAge(familyMemberList, 50);

			if (hasAboveAge50 == false) {
				iterator.remove();
				continue;
			}
		}
		return householdList;
	}

	// bsg = Baby Sunshine Grant
	// children younger than 5
	@GetMapping("/bsg")
	public List<HouseHold> retrieveBSGHouseHold() {
		List<HouseHold> householdList = houseHoldRepository.findAll();

		Iterator<HouseHold> iterator = householdList.iterator();
		while (iterator.hasNext()) {
			List<FamilyMember> familyMemberList = iterator.next().getFamilyMembers();
			boolean hasBelowAge5 = belowAge(familyMemberList, 5);

			if (hasBelowAge5 == false) {
				iterator.remove();
				continue;
			}
		}
		return householdList;
	}

	// Ygg = yolo gst grant
	// annual income less than 100,000
	@GetMapping("/ygg")
	public List<HouseHold> retrieveYGGHouseHold() {
		List<HouseHold> householdList = houseHoldRepository.findAll();

		Iterator<HouseHold> iterator = householdList.iterator();
		while (iterator.hasNext()) {
			List<FamilyMember> familyMemberList = iterator.next().getFamilyMembers();
			int annualIncome = calculateIncome(familyMemberList);

			if (annualIncome >= 100000) {
				iterator.remove();
				continue;
			}
		}
		return householdList;
	}

	
	private Integer calculateIncome(List<FamilyMember> familyMemberList) {
		Integer totalIncome = 0;
		for (FamilyMember familyMember : familyMemberList) {
			totalIncome += familyMember.getAnnualIncome();
		}
		return totalIncome;
	}

	private boolean belowAge(List<FamilyMember> familyMemberList, int age) {
		for (FamilyMember familyMember : familyMemberList) {
			if (getAge(familyMember) < age) {
				log.info("hasBelowAge " + familyMember.getDob().toString());
				return true;
			}
		}
		return false;
	}

	private boolean aboveAge(List<FamilyMember> familyMemberList, int age) {
		for (FamilyMember familyMember : familyMemberList) {
			if (getAge(familyMember) > age) {
				log.info("hasAboveAge " + familyMember.getDob().toString());
				return true;
			}
		}
		return false;
	}

	private int getAge(FamilyMember familyMember) {
		// get the age based on DOB and current date
		long diffInMillies = Math.abs(new Date().getTime() - familyMember.getDob().getTime());
		long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		return (int) Math.floorDiv(diff, 365);
	}

	private boolean hasSpouse(List<FamilyMember> familyMemberList) {
		for (FamilyMember familyMember : familyMemberList) {
			if (!(familyMember.getSpouse().equals(""))) {
				for (FamilyMember familyMember2 : familyMemberList) {
					if (familyMember2.getName().equals(familyMember.getSpouse())) {
						log.info("hasSpouse " + familyMember.getName().toString());
						return true;
					}
				}
			}
		}
		return false;
	}
}
