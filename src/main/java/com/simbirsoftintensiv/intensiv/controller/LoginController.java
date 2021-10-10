package com.simbirsoftintensiv.intensiv.controller;

import com.simbirsoftintensiv.intensiv.service.OtpService;
import com.simbirsoftintensiv.intensiv.service.SmsService;
import com.simbirsoftintensiv.intensiv.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

// как должно работать
//фронт делает запрос на /username?username=22
//ему приходит json c полями
//        map.put("Code", 200);
//        map.put("smsPassword", null);
//        map.put("username", "false");
//        где он получает правильное или не правильно имя пользователя ввел
//+ смс пароль -> пока временно потом прикручу стороний сервис
//и после этого фронт делает следующий запрос на /login?username=22&password=741777
//и сеcсия должна авторизоваться(что вроде и делает)
@Controller
//@RestController
//TODO когда не нужен будет jsp поменять на RestController
public class LoginController {
    //TODO сделать обработку ошибок (ввод неправильного пароля и ввод неправльного имени)
    //@exceptionHandler или @ControllerAdvice
    private Long UserName;
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
    public HashMap<String, String> addUser(@RequestParam(value = "username") Long username) {
        int smsPassword = otpService.generateOTP(username);
        boolean itRightName = userService.haveLoginInDB(username);
        HashMap<String, String> map = new HashMap<>();

        if(username== null) {
            map.put("сode", "400");
            map.put("smsPassword", null);
            map.put("username", "false");
        }
        if (!itRightName) {
            map.put("сode", "404");
            map.put("smsPassword", null);
            map.put("username", "false");
            return map;
        }
        SmsService.main(otpService.getOtp(username));
        map.put("username", String.valueOf(username));
        map.put("Code", "200 OK");
        map.put("smsPassword", String.valueOf(smsPassword));// временно
        this.UserName = username;


        return map;
    }

}
