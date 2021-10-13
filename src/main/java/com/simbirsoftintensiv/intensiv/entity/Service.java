package com.simbirsoftintensiv.intensiv.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "services")
public class Service extends AbstractBaseEntity {

    @Column(name = "name")
    private String name;

    public Service() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}