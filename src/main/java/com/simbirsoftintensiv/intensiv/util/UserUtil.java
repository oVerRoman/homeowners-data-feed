package com.simbirsoftintensiv.intensiv.util;

import com.simbirsoftintensiv.intensiv.entity.Address;
import com.simbirsoftintensiv.intensiv.entity.Role;
import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.to.AbstractUserTo;
import com.simbirsoftintensiv.intensiv.to.UserTo;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserUtil {

    public static User toEntity(AbstractUserTo userTo) {
        String[] rolesFromTo = userTo.getRoles().split(",");
        Set<Role> roles = Arrays.stream(rolesFromTo).map(text -> Role.fromString(text)).collect(Collectors.toSet());
        User created = new User(
                Long.parseLong(userTo.getPhone()),
                userTo.getEmail(),
                userTo.getFirstName(),
                userTo.getSecondName(),
                userTo.getPatronymic(),
                roles);

        Address address = new Address(
                userTo.getCity(),
                userTo.getStreet(),
                userTo.getHouse(),
                userTo.getBuilding(),
                userTo.getApartment());

        created.setAddress(address);
        return created;
    }

    public static List<UserTo> asTos(List<User> users) {
        return users.stream().map(UserUtil::asTo).collect(Collectors.toList());
    }

    public static UserTo asTo(User user) {
        return new UserTo(
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
                user.getAddress().getApartment(),
                user.getRoles()
                        .stream()
                        .map(Enum::toString)
                        .collect(Collectors.joining(",")));
    }

    public static User updateFromTo(User user, UserTo userTo) {

        Address address = user.getAddress();
        address.setCity(userTo.getCity());
        address.setStreet(userTo.getStreet());
        address.setHouse(userTo.getHouse());
        address.setBuilding(userTo.getBuilding());
        address.setApartment(userTo.getApartment());

        user.setFirstName(userTo.getFirstName());
        user.setSecondName(userTo.getSecondName());
        user.setPatronymic(userTo.getPatronymic());
        user.setEmail(userTo.getEmail());

        return user;
    }
}
