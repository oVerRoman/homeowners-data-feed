package com.simbirsoftintensiv.intensiv.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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

//    @JoinColumn(name = "user_id")
//    @OneToOne(fetch = FetchType.LAZY)
//    private User user;


}
