package com.studyprogramming.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "vendor")
public class Vendor extends BaseEntity{
    @Column()
    private String name;

    @Column()
    private String contactName;

    @Column()
    private String contactTitle;

    @Column()
    private String phone;

    @Column()
    private String email;

    @Column()
    private String address;

    @Column()
    private String city;

    @Column()
    private String state;

    @Column()
    private String country;

    @Column()
    private String postalCode;

    @Column()
    private String website;

    @Column()
    private String notes;

    @Column()
    private String status;

    @Column()
    private String fax;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contactName;
    }

    public void setContact(String contact) {
        this.contactName = contact;
    }

    public String getTitle() {
        return contactTitle;
    }

    public void setTitle(String title) {
        this.contactTitle = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
}
