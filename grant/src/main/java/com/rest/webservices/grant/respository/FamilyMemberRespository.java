package com.rest.webservices.grant.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rest.webservices.grant.model.FamilyMember;
import com.rest.webservices.grant.model.HouseHold;

@Repository

public interface FamilyMemberRespository extends JpaRepository<FamilyMember, Integer> {

	public List<HouseHold> findStudent(@Param("belowAge") Integer belowAge, @Param("incomeLimit") long incomeLimit);

	public List<HouseHold> findFamily(@Param("belowAge") Integer belowAge);

	public List<HouseHold> findElder(@Param("aboveAge") Integer aboveAge, @Param("houseHoldType") String houseHoldType);

	// public List<HouseHold> findYolo(@Param("houseHoldType") String
	// houseHoldType);
	public List<HouseHold> findYolo(@Param("houseHoldType") String houseHoldType,
			@Param("incomeLimit") long income1);

	public List<HouseHold> findBelowAge(@Param("belowAge") Integer belowAge);

	public List<HouseHold> findAboveAge(@Param("aboveAge") Integer aboveAge);

	public List<HouseHold> findBelowIncome(@Param("incomeLimit") long incomeLimit);

	public List<HouseHold> findSpouse();

}
