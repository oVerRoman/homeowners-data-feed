package com.simbirsoftintensiv.intensiv.to;

import com.simbirsoftintensiv.intensiv.entity.HasId;

import java.beans.ConstructorProperties;
import java.io.Serializable;

public class CreateUserTo extends AbstractUserTo implements HasId, Serializable {

    private final String otp;

    @ConstructorProperties({"id", "phone", "email", "firstName", "secondName", "patronymic", "city", "street", "house",
            "building", "apartment", "roles"})
    public CreateUserTo(String phone, String email, String firstName, String secondName,
                        String patronymic, String city, String street, String house, String building,
                        String apartment, String roles, String otp) {

        super(phone, email, firstName, secondName, patronymic,
                city, street, house, building, apartment, roles);
        this.otp = otp;
    }

    public String getOtp() {
        return otp;
    }

}
