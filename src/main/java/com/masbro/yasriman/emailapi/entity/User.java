package com.masbro.yasriman.emailapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.util.Base64;

@Entity
@Table(name = "accounts")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountid")
    private int id;

    @Column(name = "supervisorid")
    private Integer supervisorid;

    @Column(name = "accountrole")
    private String role;

    @Column(name = "accountusername")
    private String username;

    @Column(name = "accountpassword")
    private String password;

    @Column(name = "accountemail")
    private String email;

    @Column(name = "accountfirstname")
    private String firstname;

    @Column(name = "accountlastname")
    private String lastname;

    @Column(name = "accountphonenum")
    private String phonenum;

    @Column(name = "accountstreet")
    private String street;

    @Column(name = "accountstate")
    private String state;

    @Column(name = "accountcity")
    private String city;

    @Column(name = "accountpostalcode")
    private Integer postalcode;

    @Lob
    @Transient
    private byte[] picture;

    @Transient
    private String pictureBase64;

    // Constructors
    public User() {}

    public User(String firstname, String lastname, String username, String email, String password, String phonenum) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phonenum = phonenum;
        this.role = "Customer"; // Default role
    }

    public User(int id, Integer supervisorid, String role, String username, String password, String email, String firstname, String lastname, String phonenum,
                String street, String state, String city, Integer postalcode, byte[] picture) {
        this.id = id;
        this.supervisorid = supervisorid;
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

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getSupervisorid() {
        return supervisorid;
    }

    public void setSupervisorid(Integer supervisorid) {
        this.supervisorid = supervisorid;
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

    public Integer getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(Integer postalcode) {
        this.postalcode = postalcode;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureBase64() {
        return pictureBase64;
    }

    public void setPictureBase64(String pictureBase64) {
        this.pictureBase64 = pictureBase64;
        if (pictureBase64 != null && !pictureBase64.isEmpty()) {
            this.picture = Base64.getDecoder().decode(pictureBase64);
        } else {
            this.picture = null;
        }
    }
}
