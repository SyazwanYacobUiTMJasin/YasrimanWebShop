package com.masbro.yasriman.model;

public class accounts {

	private int id;
	private String role;
	private String username;
	private String password;
	private String email;
	private String firstname;
	private String lastname;
	private String phonenum;
	private String street;
	private String state;
	private String city;
	private int postalcode;
	private byte[] picture;
	private String supervisor;
	
	public accounts() {
	}
	
	public accounts(String firstname, String lastname, String username, String email, String password, String phonenum) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.phonenum = phonenum;
	}
	
	 
	public accounts(int id, String role, String username, String password, String email, String firstname, String lastname, String phonenum,
			String street, String state, String city, int postalcode, byte[] picture) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.role = role;
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username; 
		this.password = password;
		this.email = email;
		this.role = role;
		this.phonenum = phonenum;
		this.street = street;
		this.state = state;
		this.city = city; 
		this.postalcode = postalcode;
		this.picture = picture;
	}
	
	public accounts(int id, String role, String username, String password, String email, String firstname, String lastname, String phonenum,
		            String street, String state, String city, int postalcode, byte[] picture, String supervisor) {
			this.id = id;
			this.role = role;
			this.firstname = firstname;
			this.lastname = lastname;
			this.username = username;
			this.password = password;
			this.email = email;
			this.role = role;
			this.phonenum = phonenum;
			this.street = street;
			this.state = state;
			this.city = city;
			this.postalcode = postalcode;
			this.picture = picture;
			this.supervisor = supervisor;
	}
		
		public String getSupervisor() {
	        return supervisor;
	    }

	    public void setSupervisor(String supervisor) {
	        this.supervisor = supervisor;
	    }

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getPhonenum() {
		return phonenum;
	}
	
	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
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
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public int getPostalcode() {
		return postalcode;
	}
	
	public void setPostalcode(int postalcode) {
		this.postalcode = postalcode;
	}
	
	public byte[] getPicture() {
		return picture;
	}
	
	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
}
