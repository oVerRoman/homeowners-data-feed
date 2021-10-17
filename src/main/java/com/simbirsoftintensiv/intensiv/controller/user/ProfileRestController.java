package com.simbirsoftintensiv.intensiv.controller.user;

import com.simbirsoftintensiv.intensiv.AuthorizedUser;
import com.simbirsoftintensiv.intensiv.to.CreateUserTo;
import com.simbirsoftintensiv.intensiv.to.UserTo;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Контроллер профиля авторизованного юзера")
@RequestMapping(value = "/rest/profile")
public class ProfileRestController {

    @GetMapping()
    public UserTo get(@Parameter(hidden = true) @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        return authorizedUser.getUserTo();
    }

    @GetMapping(value = "/update")
    public CreateUserTo getUserForUpdate(@Parameter(hidden = true) @AuthenticationPrincipal AuthorizedUser authorizedUser){
        return authorizedUser.getCreateUserTo();
    }

    @PutMapping(value = "/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody CreateUserTo createUserTo,
                       @Parameter(hidden = true) @AuthenticationPrincipal AuthorizedUser authorizedUser){

    }

}
