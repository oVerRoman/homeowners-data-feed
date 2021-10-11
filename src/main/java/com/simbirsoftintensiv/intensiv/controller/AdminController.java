package com.simbirsoftintensiv.intensiv.controller;

import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.exception_handling.NoSuchUserException;
import com.simbirsoftintensiv.intensiv.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
                                              @RequestParam(defaultValue = "") String action) {

        HashMap<String, String> map = new HashMap<>();
        if (action.equals("DELETE")) {
            boolean itDelete = userService.delete(userId);
            if (itDelete) {
                map.put("code", "200 OK");
            } else {
                map.put("code", "404");
            }
        }
        return map;
    }

    @GetMapping("/admin/gt/{userId}")
    public HashMap gtUser(@PathVariable("userId") String userId) {
        Integer rightId;
        List<String> code ;
        HashMap<String, List> map = new HashMap<>();
        try {
              rightId =Integer.parseInt(userId);
        }
        catch (NumberFormatException exception){
            code= Collections.singletonList("400");
            map.put("code", code);
            return map;
        }
        List User = userService.usergtList(rightId);

        if (userList().size() == 1) {
            code= Collections.singletonList("404");
        }else {
            System.out.println(userList().size());
            code= Collections.singletonList("200");
        }
        map.put("Content", User);
        map.put("code", code);
        return map;
    }

    @ResponseBody
    @GetMapping("/admin/count")
    public HashMap<String, Integer> getUsersCount() {
         HashMap<String, Integer> map = new HashMap<>();
        Integer countUsers = userService.getAll().size();
        map.put("code", 200);
        map.put("Content", countUsers);
        return map;
    }

}
