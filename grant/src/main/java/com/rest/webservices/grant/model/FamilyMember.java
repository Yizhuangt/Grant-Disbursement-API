package com.rest.webservices.grant.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity // map to sql table
public class FamilyMember {

	@Id // Primary Key
	@GeneratedValue
	private Integer id;

	private String name;
	private String gender;
	private String maritalStatus;
	private String spouse;
	private String occupationType;
	private Integer annualIncome;
	private Date dob;

	public FamilyMember(Integer id, String name, String gender, String maritalStatus, String spouse,
			String occupationType, Integer annualIncome, Date dob) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.maritalStatus = maritalStatus;
		this.spouse = spouse;
		this.occupationType = occupationType;
		this.annualIncome = annualIncome;
		this.dob = dob;
	}

	protected FamilyMember() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getSpouse() {
		return spouse;
	}

	public void setSpouse(String spouse) {
		this.spouse = spouse;
	}

	public String getOccupationType() {
		return occupationType;
	}

	public void setOccupationType(String occupationType) {
		this.occupationType = occupationType;
	}

	public Integer getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(Integer annualIncome) {
		this.annualIncome = annualIncome;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	@Override
	public String toString() {
		return "FamilyMember [id=" + id + ", name=" + name + ", gender=" + gender + ", maritalStatus=" + maritalStatus
				+ ", spouse=" + spouse + ", occupationType=" + occupationType + ", annualIncome=" + annualIncome
				+ ", dob=" + dob + "]";
	}

}
