package com.simbirsoftintensiv.intensiv.controller;

import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller

public class LoginController {
    String UserName;
    @Autowired
    private UserService userService; // внедряем обьект

    //TODO сделать проверку если такой логин и вывод ошибки
    //TODO сделать проверку пароля и вывод ошибки
    //TODO сделать что было /username/${логин}
    @GetMapping("/username")
    public String checkUser(Model model,
                            @RequestParam(value = "username", required = false) String username
    ) {

        return "username";
    }

    @PostMapping("/username")
    public String addUser(@ModelAttribute("userForm")
                          @Valid User userForm, BindingResult bindingResult,
                          @RequestParam(value = "username", required = false) String username,
                          Model model) {

//        if (bindingResult.hasErrors()) {
//            return "username";
//        }
//
//        if (!userService.saveUser(userForm)) {
//            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
//            return "username";
//        }
        this.UserName = username;
        model.addAttribute("username", username);
        System.out.println(username);
        return "redirect:password";
    }

    @GetMapping("/password")
    public String checkPassword(Model model
    ) {
        model.addAttribute("username", this.UserName);

        return "password";
    }

}
