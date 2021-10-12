package com.simbirsoftintensiv.intensiv;

import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.to.UserTo;
import com.simbirsoftintensiv.intensiv.util.UserUtil;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = 1L;

    private UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getEmail(),
                null,
                user.isEnabled(),
                true, true, true,
                user.getRoles());
        System.out.println("AuthorizedUser");
        this.userTo = UserUtil.asTo(user);
    }

    public int getId() {
        return userTo.id();
    }

}



