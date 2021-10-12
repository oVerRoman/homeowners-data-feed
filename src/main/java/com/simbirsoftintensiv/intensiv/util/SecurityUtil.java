package com.simbirsoftintensiv.intensiv.util;

public class SecurityUtil {

   /* private SecurityUtil() {
    }

    public static AuthorizedUser get() {
        return requireNonNull(safeGet(), "No authorized user found");
    }

    public static AuthorizedUser safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        if (principal instanceof AuthorizedUser) {
            return (AuthorizedUser) principal;
        } else  return null;
    }

    public static int authUserId() {
        return get().getUserTo().id();
    }*/
}
