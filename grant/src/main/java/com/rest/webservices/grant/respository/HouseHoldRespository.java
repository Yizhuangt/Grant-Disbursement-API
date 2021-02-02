package com.rest.webservices.grant.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rest.webservices.grant.model.HouseHold;

@Repository
public interface HouseHoldRespository extends JpaRepository<HouseHold, Integer> {

}
