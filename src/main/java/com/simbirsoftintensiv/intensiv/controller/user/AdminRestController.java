package com.simbirsoftintensiv.intensiv.controller.user;

import com.simbirsoftintensiv.intensiv.controller.LoginController;
import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.exception_handling.NoSuchUserException;
import com.simbirsoftintensiv.intensiv.service.user.UserService;
import com.simbirsoftintensiv.intensiv.to.UserTo;
import com.simbirsoftintensiv.intensiv.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/rest/admin/users")
public class AdminRestController {
    static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private final UserService userService;

    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserTo> getAll() {
        log.info("Админ получил данные на всех пользователей.");
//TODO а если несколько админов то надо будет сделать какой конкретно
        return userService.getAll()
                .stream()
                .map(UserUtil::asTo)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{phone}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("phone") long phone) {
        User user = userService.getByPhone(phone);
        if (user == null) {
            log.warn("Попытка удаление не существующего пользователя.");
            throw new NoSuchUserException("User" +phone + " not find ");
        }
        log.warn("User " + phone + "deleting.");

        userService.delete(phone);
    }

    @GetMapping("/{phone}")
    public UserTo get(@PathVariable("phone") long phone) {
        User user = userService.getByPhone(phone);
        if (user == null) {
            log.warn("Попытка получение данных не существующего пользователя.");
            throw new NoSuchUserException("User" +phone + " not find ");
        }
        log.info("Админ получил данные на пользователя " + phone);
        return UserUtil.asTo(userService.getByPhone(phone));
    }

    @ResponseBody
    @GetMapping("/count")
    public HashMap<String, Integer> getUsersCount() {
        HashMap<String, Integer> map = new HashMap<>();
        Integer countUsers = userService.getAll().size();
        map.put("allUsers", countUsers);
        log.info("Админ запрашивал кол-во пользователей");

        return map;
    }

}
