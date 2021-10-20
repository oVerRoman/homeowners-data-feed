package com.simbirsoftintensiv.intensiv.to;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.simbirsoftintensiv.intensiv.entity.HasId;
import com.simbirsoftintensiv.intensiv.exception_handling.IncorectDataDuringRegistration;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.HashMap;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, isGetterVisibility = NONE, setterVisibility = NONE)
public class UserTo implements HasId, Serializable {

    private Integer id;
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

    private final String roles;


    @ConstructorProperties({"id", "phone", "email", "firstName", "secondName", "patronymic", "city", "street", "house",
            "building", "apartment", "roles"})
    public UserTo(Integer id, String phone, String email, String firstName, String secondName, String patronymic,
                  String city, String street, String house, String building, String apartment, String roles) {
        this.id = id;
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
        this.roles = roles;
    }

    public String getPhone() {
        if (phone.length() < 11) {
            throw new IncorectDataDuringRegistration("Length " + phone + " < 11");
        }
        if (phone.length() > 11) {
            throw new IncorectDataDuringRegistration("Length " + phone + " > 11");
        }
        char firstSymbol = '7';
        char secondSymbol = '9';
        System.out.println("getPhone " + phone.charAt(1));

        if (phone.charAt(0) != firstSymbol) {
            throw new IncorectDataDuringRegistration("The first digit is not 7.");
        }
        if (phone.charAt(1) != secondSymbol) {
            throw new IncorectDataDuringRegistration("The second digit is not 9.");
        }

        return phone;
    }

    public String getEmail() {
        String checkEmail = "@";
        if (!email.contains(checkEmail)) {
            throw new IncorectDataDuringRegistration("This no email.");
        }

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

    public String getRoles() {
        return roles;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public HashMap<String, String> info() {
        String[] words = address.substring(7).split(", user=com");
        String addressForFront = words[0]+"}";
        HashMap<String, String> map = new HashMap<>();
        map.put("user", String.valueOf(phone));
        map.put("email", email);
        map.put("firstName", firstName);
        map.put("secondName", secondName);
        map.put("patronymic", patronymic);
        map.put("address", addressForFront);

        return map;
    }
}
