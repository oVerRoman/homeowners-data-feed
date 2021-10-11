package com.simbirsoftintensiv.intensiv.controller.user;

import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.service.user.UserService;
import com.simbirsoftintensiv.intensiv.to.CreateUserTo;
import com.simbirsoftintensiv.intensiv.to.UserTo;
import com.simbirsoftintensiv.intensiv.util.UserUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/rest/users")
public class RegistrationRestController {

    private final UserService userService;

    public RegistrationRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<UserTo> create(@RequestBody CreateUserTo createUserTo) {
        User userFromTo = UserUtil.toEntity(createUserTo);
        UserTo created = UserUtil.asTo(userService.create(userFromTo));
        URI uriOfNewUser = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/rest/admin/users" + "/{phone}")
                .buildAndExpand(created.getPhone()).toUri();
        return ResponseEntity.created(uriOfNewUser).body(created);
    }

}
