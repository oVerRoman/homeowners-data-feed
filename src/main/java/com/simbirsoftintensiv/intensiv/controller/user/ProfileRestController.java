package com.simbirsoftintensiv.intensiv.controller.user;

import com.simbirsoftintensiv.intensiv.AuthorizedUser;
import com.simbirsoftintensiv.intensiv.service.user.UserService;
import com.simbirsoftintensiv.intensiv.to.UserTo;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Контроллер профиля авторизованного юзера")
@RequestMapping(value = ProfileRestController.REST_URL)
public class ProfileRestController {

    public static final String REST_URL = "/rest/profile";

    private final UserService userService;

    public ProfileRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public UserTo get(@Parameter(hidden = true) @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        return authorizedUser.getUserTo();
    }

    @GetMapping(value = "/update")
    public UserTo getUserForUpdate(
            @Parameter(hidden = true) @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        return authorizedUser.getUserTo();
    }

    @PutMapping(value = "/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserTo userTo,
                       @Parameter(hidden = true) @AuthenticationPrincipal AuthorizedUser authorizedUser) {

        userService.update(userTo, authorizedUser.getId());
    }

}
