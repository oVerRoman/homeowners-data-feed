package com.simbirsoftintensiv.intensiv.controller;

import com.simbirsoftintensiv.intensiv.service.OtpService;
import com.simbirsoftintensiv.intensiv.service.SmsService;
import com.simbirsoftintensiv.intensiv.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@Controller

public class LoginController {
    //TODO сделать обработку ошибок (ввод неправильного пароля и ввод неправльного имени)
    //@exceptionHandler или @ControllerAdvice
    @Autowired
    private UserService userService; // внедряем обьект

    @Autowired
    public OtpService otpService;


    // фронту это не надо будет
    @GetMapping("/username")
    public String checkUser() {
        return "username";
    }

    @ResponseBody
    @PostMapping("/username")
    public HashMap<String, String> addUser(@RequestParam(value = "username") String username) {
        Long rightUsername;
        HashMap<String, String> map = new HashMap<>();
        try {
            rightUsername = Long.parseLong(username);
        } catch (NumberFormatException exception) {
            map.put("сode", "400");
            map.put("smsPassword", null);
            map.put("username", "false");
            return map;
        }

        int smsPassword = otpService.generateOTP(rightUsername);
        boolean itRightName = userService.haveLoginInDB(rightUsername);
        if (!itRightName) {
            map.put("сode", "404");
            map.put("smsPassword", null);
            map.put("username", "false");
            return map;
        }
        SmsService.main(otpService.getOtp(rightUsername));
        map.put("username", username);
        map.put("Code", "200 OK");
        map.put("smsPassword", String.valueOf(smsPassword));// временно
        return map;
    }

}
