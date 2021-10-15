package com.simbirsoftintensiv.intensiv.to;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.simbirsoftintensiv.intensiv.entity.HasId;

import java.beans.ConstructorProperties;
import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, isGetterVisibility = NONE, setterVisibility = NONE)
public class UserTo implements HasId, Serializable {

    protected Integer id;
    private final Long phone;
    private final String email;
    private final String firstName;
    private final String secondName;
    private final String patronymic;
    private final String address;
    private final String company;

    @ConstructorProperties({"id", "phone", "email", "firstName", "secondName", "patronymic", "address", "company"})
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

    public Long getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getAddress() {
        return address;
    }

    public String getCompany() {
        return company;
    }

    public String info() {
        return "user:" + phone + "," + "email:" + email + "," + "firstName: " + firstName + "," + "secondName:" + secondName + "," +
                "patronymic:" + patronymic + "," + "address:" + address + "," + "company" + company;
    }
}
