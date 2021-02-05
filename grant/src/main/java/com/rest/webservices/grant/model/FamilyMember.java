package com.rest.webservices.grant.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.TableGenerator;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

@Entity // map to sql table
@NamedQueries({
		@NamedQuery(name = "FamilyMember.findFamily", query = "SELECT distinct(fm.houseHold) FROM FamilyMember fm WHERE (DATEDIFF(hour,fm.dob ,sysdate())/8766) < :belowAge AND fm.houseHold IN (SELECT fm.houseHold FROM FamilyMember fm WHERE fm.spouse<>'' Group by fm.houseHold HAVING COUNT(fm.spouse)>1)"),
		@NamedQuery(name = "FamilyMember.findStudent", query = "SELECT distinct(fm.houseHold) FROM FamilyMember fm INNER JOIN HouseHold hh ON hh.id =fm.houseHold WHERE (DATEDIFF(hour,fm.dob,sysdate())/8766) < :belowAge AND hh.id IN (SELECT fm.houseHold FROM fm Group by fm.houseHold Having SUM(fm.annualIncome) < :incomeLimit)"),
		@NamedQuery(name = "FamilyMember.findElder", query = "SELECT distinct(fm.houseHold) FROM FamilyMember fm WHERE (DATEDIFF(hour,fm.dob ,sysdate())/8766) > :aboveAge AND fm.houseHold IN (SELECT hh.id from HouseHold hh WHERE lower(hh.houseHoldType) = :houseHoldType)"),
		@NamedQuery(name = "FamilyMember.findYolo", query = "SELECT distinct(fm.houseHold) FROM FamilyMember fm INNER JOIN HouseHold hh ON hh.id =fm.houseHold WHERE lower(hh.houseHoldType) = :houseHoldType GROUP BY fm.houseHold HAVING SUM(fm.annualIncome) < :incomeLimit"),
		@NamedQuery(name = "FamilyMember.findBelowAge", query = "SELECT distinct(fm.houseHold) FROM FamilyMember fm WHERE (DATEDIFF(hour,fm.dob ,sysdate())/8766) < :belowAge"),
		@NamedQuery(name = "FamilyMember.findAboveAge", query = "SELECT distinct(fm.houseHold) FROM FamilyMember fm WHERE (DATEDIFF(hour,fm.dob ,sysdate())/8766) > :aboveAge"),
		@NamedQuery(name = "FamilyMember.findBelowIncome", query = "SELECT distinct(fm.houseHold) FROM FamilyMember fm GROUP BY fm.houseHold HAVING SUM(fm.annualIncome) < :incomeLimit"),
		@NamedQuery(name = "FamilyMember.findSpouse", query = "SELECT fm.houseHold FROM FamilyMember fm WHERE fm.spouse<>'' Group by fm.houseHold HAVING COUNT(fm.spouse)>1") })

public class FamilyMember {

	@Id // Primary Key
	@TableGenerator(name = "id", initialValue = 213)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "id") // database to generate id
//	@JsonIgnore
	private Integer id;

	@Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
	private String name;

	@NotNull
	private String gender;

	@NotNull
	private String maritalStatus;

	private String spouse;

	@NotNull
	private String occupationType;

	@NotNull
	private Integer annualIncome;

	@Past
	private Date dob;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore // Prevent
	private HouseHold houseHold;

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

	public HouseHold getHouseHold() {
		return houseHold;
	}

	public void setHouseHold(HouseHold houseHold) {
		this.houseHold = houseHold;
	}

	@Override
	public String toString() {
		return "FamilyMember [id=" + id + ", name=" + name + ", gender=" + gender + ", maritalStatus=" + maritalStatus
				+ ", spouse=" + spouse + ", occupationType=" + occupationType + ", annualIncome=" + annualIncome
				+ ", dob=" + dob + "]";
	}

}
