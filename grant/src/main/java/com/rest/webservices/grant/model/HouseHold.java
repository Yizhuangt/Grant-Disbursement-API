package com.rest.webservices.grant.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

@Entity
@NamedQueries({
		@NamedQuery(name = "HouseHold.findByHouseholdType", query = "from HouseHold hh WHERE lower(hh.houseHoldType) = :householdType") })

public class HouseHold {

	@Id
	@TableGenerator(name = "id", initialValue = 5)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "id")
	private Integer id;

	private String houseHoldType;

	@OneToMany(mappedBy = "houseHold", cascade = CascadeType.REMOVE)
	private List<FamilyMember> familyMembers;

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

	public List<FamilyMember> getFamilyMembers() {
		return familyMembers;
	}

	public void setFamilyMembers(List<FamilyMember> familyMembers) {
		this.familyMembers = familyMembers;
	}

	@Override
	public String toString() {
		return "HouseHold [id=" + id + ", houseHoldType=" + houseHoldType + "]";
	}

}
