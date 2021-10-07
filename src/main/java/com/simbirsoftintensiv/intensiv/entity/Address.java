package com.simbirsoftintensiv.intensiv.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "addresses")
public class Address extends AbstractBaseEntity{

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

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "address")
    private User user;

    public Address() {}

    public Address(String city, String street, String house, String building, String apartment) {
        this.city = city;
        this.street = street;
        this.house = house;
        this.building = building;
        this.apartment = apartment;
    }
}
