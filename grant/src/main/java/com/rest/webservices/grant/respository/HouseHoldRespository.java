package com.rest.webservices.grant.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rest.webservices.grant.model.HouseHold;

@Repository
public interface HouseHoldRespository extends JpaRepository<HouseHold, Integer> {

	public List<HouseHold> findByHouseholdType(@Param("householdType") String household_type);

}
