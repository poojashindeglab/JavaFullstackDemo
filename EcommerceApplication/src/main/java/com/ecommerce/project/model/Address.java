package com.ecommerce.project.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id")
	Integer addressId;
	
	@Column(name = "building_name")
	private String buildingName;
	
	@Column(name = "street")
	private String street;
	
	@Size(min = 2, message = "State name must be at least 2 character")
	@Column(name = "state")
	private String state;
	
	@Size(min = 2, message = "Country must be at lease 2 character")
	@Column(name = "country")
	private String country;
	
	@NotBlank
	@Size(min =5, message = "Pincode must be at least 5 character")
	private String pincode;
	
	@ManyToMany(mappedBy = "addresses")
	private List<User> users = new ArrayList<User>();

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
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

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	
	public Address() {
		super();
	}

	public Address(Integer addressId, String buildingName, String street,
			@Size(min = 2, message = "State name must be at least 2 character") String state,
			@Size(min = 2, message = "Country must be at lease 2 character") String country,
			@NotBlank @Size(min = 5, message = "Pincode must be at least 5 character") String pincode
			) {
		super();
		this.addressId = addressId;
		this.buildingName = buildingName;
		this.street = street;
		this.state = state;
		this.country = country;
		this.pincode = pincode;
	}
	
	
	
}
