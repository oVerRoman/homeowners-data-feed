package com.simbirsoftintensiv.intensiv;

import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.to.UserTo;
import com.simbirsoftintensiv.intensiv.util.UserUtil;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = 1L;

    private UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getPhone().toString(),
                "1asdfasdfqwerdfiji93(*432039)32", user.isEnabled(),
                true, true, true,
                user.getRoles());
        this.userTo = UserUtil.asTo(user);
    }

    public long getPhone(){
        return Long.parseLong(userTo.getPhone());
    }

    public int getId() {
        return userTo.id();
    }

    public UserTo getUserTo() {
        return userTo;
    }

    public void setUserTo(UserTo userTo) {
        this.userTo = userTo;
    }
}



