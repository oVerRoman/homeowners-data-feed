package com.simbirsoftintensiv.intensiv.controller;

import com.simbirsoftintensiv.intensiv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;
 
//Доступ к странице admin имеют только пользователи с ролью администратора.
    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "admin";
    }

    @PostMapping("/admin")
<<<<<<< src/main/java/com/simbirsoftintensiv/intensiv/controller/AdminController.java
    public String deleteUser(@RequestParam(required = true, defaultValue = "") Long userId,
                             @RequestParam(required = true, defaultValue = "") String action,
                             Model model) {
        // Вместо анотации использовать PathVariable ???
        if (action.equals("delete")) {
=======
    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model) {
        if (action.equals("delete")){
>>>>>>> src/main/java/com/simbirsoftintensiv/intensiv/controller/AdminController.java
            userService.deleteUser(userId);
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin/gt/{userId}")
<<<<<<< src/main/java/com/simbirsoftintensiv/intensiv/controller/AdminController.java
    public String gtUser(@PathVariable("userId") Long userId, Model model) {
=======
    public String  gtUser(@PathVariable("userId") Long userId, Model model) {
>>>>>>> src/main/java/com/simbirsoftintensiv/intensiv/controller/AdminController.java
        model.addAttribute("allUsers", userService.usergtList(userId));
        return "admin";
    }
}
