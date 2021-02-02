package com.rest.webservices.grant.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class HouseHold {

	@Id
	@GeneratedValue
	private Integer id;
	private String houseHoldType;

	protected HouseHold() {

	}

	public HouseHold(String houseHoldType) {
		this.houseHoldType = houseHoldType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHouseHoldType() {
		return houseHoldType;
	}

	public void setHouseHoldType(String houseHoldType) {
		this.houseHoldType = houseHoldType;
	}

	@Override
	public String toString() {
		return "HouseHold [id=" + id + ", houseHoldType=" + houseHoldType + "]";
	}

}
