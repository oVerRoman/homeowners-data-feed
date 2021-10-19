package com.simbirsoftintensiv.intensiv.util;

import com.simbirsoftintensiv.intensiv.entity.Address;
import com.simbirsoftintensiv.intensiv.entity.Role;
import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.to.CreateUserTo;
import com.simbirsoftintensiv.intensiv.to.UserTo;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserUtil {

    public static UserTo asTo(User user) {
        return new UserTo(
                user.getId(),
                user.getPhone(),
                user.getEmail(),
                user.getFirstName(),
                user.getSecondName(),
                user.getPatronymic(),
                user.getAddress().toString(),
                user.getCompany().toString(),
                String.join(",", user.getRoles().stream().map(Enum::toString).collect(Collectors.joining())));
    }

    public static User toEntity(CreateUserTo createUserTo) {
        User created = new User(
                Long.parseLong(createUserTo.getPhone()),
                createUserTo.getEmail(),
                createUserTo.getFirstName(),
                createUserTo.getSecondName(),
                createUserTo.getPatronymic(),
                Role.USER);

        Address address = new Address(
                createUserTo.getCity(),
                createUserTo.getStreet(),
                createUserTo.getHouse(),
                createUserTo.getBuilding(),
                createUserTo.getApartment());

        created.setAddress(address);
        return created;
    }

    public static List<UserTo> asTos(List<User> users){
        return users.stream().map(UserUtil::asTo).collect(Collectors.toList());
    }

    public static CreateUserTo asCreateTo(User user) {
        return new CreateUserTo(
                user.getId(),
                user.getPhone() + "",
                user.getEmail(),
                user.getFirstName(),
                user.getSecondName(),
                user.getPatronymic(),
                user.getAddress().getCity(),
                user.getAddress().getStreet(),
                user.getAddress().getHouse(),
                user.getAddress().getBuilding(),
                user.getAddress().getApartment());
    }

    public static User updateFromTo(User user, CreateUserTo createUserTo) {

        Address address = user.getAddress();
        address.setCity(createUserTo.getCity());
        address.setStreet(createUserTo.getStreet());
        address.setHouse(createUserTo.getHouse());
        address.setBuilding(createUserTo.getBuilding());
        address.setApartment(createUserTo.getApartment());

        user.setFirstName(createUserTo.getFirstName());
        user.setSecondName(createUserTo.getSecondName());
        user.setPatronymic(createUserTo.getPatronymic());
        user.setEmail(createUserTo.getEmail());

        return user;
    }
}
