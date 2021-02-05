package com.rest.webservices.grant.controllers;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rest.webservices.grant.model.FamilyMember;
import com.rest.webservices.grant.model.HouseHold;
import com.rest.webservices.grant.respository.FamilyMemberRespository;
import com.rest.webservices.grant.respository.HouseHoldRespository;

@RestController
public class GrantController {
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(HouseHoldController.class);

	@Autowired
	private HouseHoldRespository houseHoldRepository;

	@Autowired
	private FamilyMemberRespository familyMemberRepository;

	// student = Student Encouragement Bonus
	// Children less than 16 years old
	// income less than $150,000
	@GetMapping("/student")
	public List<HouseHold> retrieveStudentHousehold(@RequestParam int age, @RequestParam long income_limit) {
		List<HouseHold> householdList = familyMemberRepository.findStudent(age, income_limit);

		Iterator<HouseHold> iterator = householdList.iterator();
		while (iterator.hasNext()) {
			// Loop through the family list and delete non qualified members
			Iterator<FamilyMember> familyIterator = iterator.next().getFamilyMembers().iterator();
			while (familyIterator.hasNext()) {
				if (getAge(familyIterator.next()) > age) {
					familyIterator.remove();
				}
			}
		}
		return householdList;
	}

	// fts - Family Together Scheme
	// household with husband & wife
	// children younger than 18
	@GetMapping("/family")
	public List<HouseHold> retrieveFamilyHouseHold(@RequestParam int age) {
		List<HouseHold> householdList = familyMemberRepository.findFamily(age);

		// Database checks if household has >= 2 spouse
		// Following method will check that both husband and wife are in same household
		boolean hasChildren = false;
		int spouseInHousehold = 0;
		Iterator<HouseHold> iterator = householdList.iterator();
		while (iterator.hasNext()) {
			// Loop through the family list and delete non qualified members
			List<FamilyMember> familyMembers = iterator.next().getFamilyMembers();
			Iterator<FamilyMember> familyIterator = familyMembers.iterator();
			while (familyIterator.hasNext()) {
				FamilyMember currentFamilyMember = familyIterator.next();
				log.info(currentFamilyMember.getName() + " " + age);

				// do not delete children, husband and wife
				boolean doNotDelete = false;
				if ((getAge(currentFamilyMember)) < age) {
					doNotDelete = true;
					hasChildren = true;
				}

				if (spouseInHousehold(familyMembers, currentFamilyMember)) {
					doNotDelete = true;
					spouseInHousehold += 1;
				}

				if (!(doNotDelete)) {
					log.info("deleting :" + currentFamilyMember.getName());
					familyIterator.remove();
				}
			}
			if (!(hasChildren && spouseInHousehold >= 2)) {
				iterator.remove();
			}
		}
		return householdList;
	}

	// eb = Elder Bonus
	// family member aged above 50
	// http://localhost:8080/elder?age=50&household_type=HDB
	@GetMapping("/elder")
	public List<HouseHold> retrieveElderHouseHold(@RequestParam int age, @RequestParam String household_type) {
		// List<HouseHold> householdList = findAboveAge(age);
		List<HouseHold> householdList = familyMemberRepository.findElder(age, household_type.toLowerCase());

		Iterator<HouseHold> iterator = householdList.iterator();
		while (iterator.hasNext()) {
			// Loop through the family list and delete non qualified members
			Iterator<FamilyMember> familyIterator = iterator.next().getFamilyMembers().iterator();
			while (familyIterator.hasNext()) {
				if (getAge(familyIterator.next()) < age) {
					familyIterator.remove();
				}
			}
		}
		return householdList;
	}

	// bsg = Baby Sunshine Grant
	// children younger than 5
	@GetMapping("/baby")
	public List<HouseHold> retrieveBabyHouseHold(@RequestParam int age) {
		// Get list of household that has baby below age
		List<HouseHold> householdList = findBelowAge(age);

		Iterator<HouseHold> iterator = householdList.iterator();
		while (iterator.hasNext()) {
			// Loop through the family list and delete non qualified members
			Iterator<FamilyMember> familyIterator = iterator.next().getFamilyMembers().iterator();
			while (familyIterator.hasNext()) {
				if (getAge(familyIterator.next()) > age) {
					familyIterator.remove();
				}
			}
		}
		return householdList;
	}

	// Ygg = yolo gst grant
	// annual income less than 100,000
	// http://localhost:8080/yolo?income=1000000&houseHoldType=HDB
	@GetMapping("/yolo")
	public List<HouseHold> findYoloHousehold(@RequestParam long income, @RequestParam String household_type) {
		log.info("income :" + income + " household_type: " + household_type);
		List<HouseHold> houseHoldByIncome = familyMemberRepository.findYolo(household_type.toLowerCase(), income);
		return houseHoldByIncome;
	}

	// ================================================

	@GetMapping("/grant")
	public List<HouseHold> findGrant(@RequestParam(required = false, defaultValue = "-1") int belowage,
			@RequestParam(required = false, defaultValue = "-1") int aboveage,
			@RequestParam(required = false, defaultValue = "-1") int income,
			@RequestParam(required = false, defaultValue = "-1") String household_type) {

		List<HouseHold> allHouseHold = houseHoldRepository.findAll();
		log.info("belowAge :" + belowage);
		log.info("aboveAge :" + aboveage);
		log.info("income :" + income);
		log.info("household_type :" + household_type);

		if (belowage != -1) {
			List<HouseHold> belowAgeHouseHold = familyMemberRepository.findBelowAge(belowage);
			allHouseHold.retainAll(belowAgeHouseHold);
		}

		if (aboveage != -1) {
			List<HouseHold> aboveAgeHouseHold = familyMemberRepository.findAboveAge(aboveage);
			allHouseHold.retainAll(aboveAgeHouseHold);
		}

		if (income != -1) {
			List<HouseHold> belowIncomeHouseHold = familyMemberRepository.findBelowIncome(income);
			allHouseHold.retainAll(belowIncomeHouseHold);
		}
	
		if (!(household_type.equalsIgnoreCase("-1"))) {
			List<HouseHold> belowIncomeHouseHold = houseHoldRepository
					.findByHouseholdType(household_type.toLowerCase());
			allHouseHold.retainAll(belowIncomeHouseHold);

		}
		return allHouseHold;

	}

	// =================================================

	@GetMapping("/findbelowage")
	public List<HouseHold> findBelowAge(@RequestParam int age) {
		log.info("findBelowAge ");
		List<HouseHold> houseHoldBelowAge = familyMemberRepository.findBelowAge(age);
		return houseHoldBelowAge;
	}

	@GetMapping("/findaboveage")
	public List<HouseHold> findAboveAge(@RequestParam int age) {
		log.info("findAboveAge " + age);
		List<HouseHold> houseHoldAboveAge = familyMemberRepository.findAboveAge(age);
		return houseHoldAboveAge;
	}

	@GetMapping("/findbyhouseholdtype")
	public List<HouseHold> findByHouseholdType(@RequestParam String household_type) {
		log.info("findbyhouseholdtype ");
		List<HouseHold> houseHoldByType = houseHoldRepository.findByHouseholdType(household_type);
		return houseHoldByType;
	}

	@GetMapping("/findbelowincome")
	public List<HouseHold> findBelowIncome(@RequestParam long income_limit) {
		log.info("findbyhouseholdtype ");
		List<HouseHold> houseHoldByIncome = familyMemberRepository.findBelowIncome(income_limit);
		return houseHoldByIncome;
	}

	@GetMapping("/findspouse")
	public List<HouseHold> findSpouse() {
		log.info("findbyhouseholdtype ");
		List<HouseHold> householdWithSpouse = familyMemberRepository.findSpouse();
		return householdWithSpouse;
	}

	// =======================================

	private int getAge(FamilyMember familyMember) {
		// get the age based on DOB and current date
		long diffInMillies = Math.abs(new Date().getTime() - familyMember.getDob().getTime());
		long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		log.info("getAge " + Math.floorDiv(diff, 365));
		return (int) Math.floorDiv(diff, 365);
	}

	private boolean spouseInHousehold(List<FamilyMember> familyMemberList, FamilyMember familyMember) {
		// check if family member has spouse in household
		if (!(familyMember.getSpouse().equals(""))) {
			// if so, check if spouse is found in same family member list
			for (FamilyMember familyMember2 : familyMemberList) {
				if (familyMember2.getName().equals(familyMember.getSpouse())) {
					log.info("hasSpouse " + familyMember.getName().toString());
					return true;
				}
			}
		}
		return false;
	}
}
