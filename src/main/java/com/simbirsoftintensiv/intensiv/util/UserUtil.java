package com.simbirsoftintensiv.intensiv.util;

import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.to.UserTo;

public class UserUtil {

//    public static User createNewFromTo(UserTo userTo) {
//        return new User(null, userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), userTo.getCaloriesPerDay(), Role.USER);
//    }

    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getPhone(), user.getEmail(), user.getFirstName(), user.getSecondName(),
                user.getPatronymic(), user.getAddress().toString(), user.getCompany().toString());
    }


//    public static User updateFromTo(User user, UserTo userTo) {
//        user.setName(userTo.getName());
//        user.setEmail(userTo.getEmail().toLowerCase());
//        user.setCaloriesPerDay(userTo.getCaloriesPerDay());
//        user.setPassword(userTo.getPassword());
//        return user;
//    }
//
//    public static User prepareToSave(User user, PasswordEncoder passwordEncoder) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setEmail(user.getEmail().toLowerCase());
//        return user;
//    }
}
