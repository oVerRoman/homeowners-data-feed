package com.simbirsoftintensiv.intensiv.util;


import com.simbirsoftintensiv.intensiv.entity.HasId;
import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.exception_handling.NotFoundException;
import com.simbirsoftintensiv.intensiv.to.UserTo;

public class ValidationUtil {

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static void checkNotFoundWithPhone(boolean found, long phone) {
        checkNotFound(found, "id=" + phone);
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static User checkNotFoundWithPhone(User user, long phone) {
        checkNotFoundWithPhone(user != null, phone);
        return user;
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkIdEquality(HasId bean, int id) {
        if (bean.id() != id) {
            throw new NotFoundException(bean + " must be with id=" + id);
        }
    }

    public static void checkPhoneEquality(UserTo userTo, long phone) {
        if (Long.parseLong(userTo.getPhone()) != phone) {
            throw new NotFoundException("User cannot change phone number! Only the administrator can..");
        }
    }

    public static void checkPhone(boolean found) {
        if (found) {
            // fixme change exception
            throw new NotFoundException("Phone number already exists!");
        }
    }


}
