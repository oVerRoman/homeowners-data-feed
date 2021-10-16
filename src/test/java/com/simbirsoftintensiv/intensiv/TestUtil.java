package com.simbirsoftintensiv.intensiv;

import com.simbirsoftintensiv.intensiv.entity.User;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

public class TestUtil {

    public static RequestPostProcessor user(User user) {
        AuthorizedUser authorizedUser = new AuthorizedUser(user);
        return SecurityMockMvcRequestPostProcessors.user(authorizedUser);
    }


}
