package com.simbirsoftintensiv.intensiv.to;

import com.simbirsoftintensiv.intensiv.entity.HasId;

import java.io.Serializable;

public class UserTo implements HasId, Serializable {

    protected Integer id;

    private Long phone;

    private String email;

    private String firstName;

    private String secondName;

    private String patronymic;

    private String address;

    private String company;

//    private String password;
//    private String passwordConfirm;
//    private Set<Role> roles;


    public UserTo(Integer id, Long phone, String email, String firstName, String secondName, String patronymic,
                  String address, String company) {
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.patronymic = patronymic;
        this.address = address;
        this.company = company;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
