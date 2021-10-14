package com.simbirsoftintensiv.intensiv.controller.user;

import com.simbirsoftintensiv.intensiv.AuthorizedUser;
import com.simbirsoftintensiv.intensiv.to.UserTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/profile")
public class ProfileRestController {

    @GetMapping()
    public UserTo get(@AuthenticationPrincipal AuthorizedUser authorizedUser) {
        return authorizedUser.getUserTo();
    }

}
