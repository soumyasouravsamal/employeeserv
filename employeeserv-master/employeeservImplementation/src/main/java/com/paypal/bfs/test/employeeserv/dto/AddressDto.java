package com.paypal.bfs.test.employeeserv.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "address")
public class AddressDto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "adr_id")
	private int id;

	@Column(name = "line1")
	@NotNull
	private String line1;

	@Column(name = "line2")
	private String line2;

	@Column(name = "city")
	@NotNull
	private String city;

	@Column(name = "state")
	@NotNull
	private String state;

	@Column(name = "country")
	@NotNull
	private String country;

	@Column(name = "zip_Code")
	@NotNull
	private Integer zipCode;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}

}
