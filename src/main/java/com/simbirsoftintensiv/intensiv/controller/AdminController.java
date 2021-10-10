package com.simbirsoftintensiv.intensiv.controller;

import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/*
После авторизации Юзера и если у него роль ADMIN
Фронт делает GET запрос на /admin
и ему приходит список/json со списком юзеров в БД
при POST запросе на /admin?action=delete&userId={userId}
происходит удаление
 */


@RestController
@RequestMapping("/rest")
public class AdminController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @GetMapping("/admin")
    public HashMap<String, List<User>> userList() {
        System.out.println("создаем массив с логинами юзеров");
        HashMap<String, List<User>> map = new HashMap<>();
        map.put("Content", userService.getAll());
        return map;
    }

    @PostMapping("/admin")
    public HashMap<String, String> deleteUser(@RequestParam(required = true, defaultValue = "") Integer userId,
                                              @RequestParam(required = true, defaultValue = "") String action,
                                              Model model) {

        HashMap<String, String> map = new HashMap<>();
        if (action.equals("DELETE")) {
            userService.delete(userId);
            map.put("code", "200 OK");
            map.put(String.valueOf(userId), action);
        }
        return map;
    }

    @GetMapping("/admin/gt/{userId}")
    public String gtUser(@PathVariable("userId") Long userId, Model model) {
        System.out.println(userId);
        model.addAttribute("allUsers", userService.usergtList(userId));
        return "admin";
    }
}
