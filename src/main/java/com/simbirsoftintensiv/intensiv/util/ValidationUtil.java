package com.simbirsoftintensiv.intensiv.util;


import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.exception_handling.NotFoundException;

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


}
