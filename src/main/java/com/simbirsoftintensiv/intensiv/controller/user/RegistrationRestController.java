package com.simbirsoftintensiv.intensiv.controller.user;

import com.simbirsoftintensiv.intensiv.controller.LoginController;
import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.service.user.UserService;
import com.simbirsoftintensiv.intensiv.to.CreateUserTo;
import com.simbirsoftintensiv.intensiv.to.UserTo;
import com.simbirsoftintensiv.intensiv.util.UserUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@Tag(name = "Контроллер регистрации")
//TODO требует прописать Content-Type: application/json
@RequestMapping(value = RegistrationRestController.REST_URL)
public class RegistrationRestController {

    static final String REST_URL = "/rest/users";

    private final UserService userService;
    static final Logger log =
            LoggerFactory.getLogger(LoginController.class);
    public RegistrationRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<UserTo> create(@RequestBody CreateUserTo createUserTo) {
        log.info("Attempt to create a new user " + createUserTo.getPhone());


        User userFromTo = UserUtil.toEntity(createUserTo);
        UserTo created = UserUtil.asTo(userService.create(userFromTo));
        URI uriOfNewUser = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/rest/admin/users" + "/{phone}")
                .buildAndExpand(created.getPhone()).toUri();
        return ResponseEntity.created(uriOfNewUser).body(created);
    }

}
