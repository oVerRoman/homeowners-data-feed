package com.simbirsoftintensiv.intensiv.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class Address extends AbstractBaseEntity {

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "house")
    private String house;

    @Column(name = "building")
    private String building;

    @Column(name = "apartment")
    private String apartment;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "address")
    private User user;

    public Address() {
    }

    public Address(String city, String street, String house, String building, String apartment) {
        this.city = city;
        this.street = street;
        this.house = house;
        this.building = building;
        this.apartment = apartment;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Address{" +
               "city='" + city + '\'' +
               ", street='" + street + '\'' +
               ", house='" + house + '\'' +
               ", building='" + building + '\'' +
               ", apartment='" + apartment + '\'' +
               ", user=" + user +
               '}';
    }
}
