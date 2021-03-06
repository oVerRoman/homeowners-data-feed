package com.simbirsoftintensiv.intensiv.to;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.beans.ConstructorProperties;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, isGetterVisibility = NONE, setterVisibility = NONE)
public class CreateUserTo {

    private final String phone;
    private final String email;
    private final String firstName;
    private final String secondName;
    private final String patronymic;
    private final String city;
    private final String street;
    private final String house;
    private final String building;
    private final String apartment;

    @ConstructorProperties({"phone", "email", "firstName", "secondName", "patronymic", "city", "street",  "house",
            "building", "apartment"})
    public CreateUserTo(String phone, String email, String firstName, String secondName, String patronymic,
                        String city, String street, String house, String building, String apartment) {
        this.phone = phone;
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.patronymic = patronymic;
        this.city = city;
        this.street = street;
        this.house = house;
        this.building = building;
        this.apartment = apartment;
    }

    public String getPhone() {
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

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getHouse() {
        return house;
    }

    public String getBuilding() {
        return building;
    }

    public String getApartment() {
        return apartment;
    }
}
