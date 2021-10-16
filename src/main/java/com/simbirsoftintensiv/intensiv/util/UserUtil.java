package com.simbirsoftintensiv.intensiv.util;

import com.simbirsoftintensiv.intensiv.entity.Address;
import com.simbirsoftintensiv.intensiv.entity.Role;
import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.to.CreateUserTo;
import com.simbirsoftintensiv.intensiv.to.UserTo;

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
                user.getCompany().toString());
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
}
