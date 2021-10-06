package com.simbirsoftintensiv.intensiv.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.simbirsoftintensiv.intensiv.service.OtpService;
import com.simbirsoftintensiv.intensiv.service.SmsService;
import com.simbirsoftintensiv.intensiv.service.user.UserService;

// как должно работать
//фронт делает запрос на /username?username=22
//ему приходит json c полями
//        map.put("rightUsername", "false");
//        map.put("smsPassword", null);
//        map.put("username", "false");
//        где он получает правильное или не правильно имя пользователя ввел
//+ смс пароль -> пока временно потом прикручу стороний сервис
//и после этого фронт делает следующий запрос на /login?username=22&password=741777
//и сеcсия должна авторизоваться(что вроде и делает)
@Controller
public class LoginController {

    // TODO сделать обработку ошибок (ввод неправильного пароля и ввод неправльного
    // имени)
    // @exceptionHandler или @ControllerAdvice
    private String UserName;
    @Autowired
    private UserService userService; // внедряем обьект

    @Autowired
    public OtpService otpService;

    // фронту это не надо будет
    @GetMapping("/username")
    public String checkUser(Model model,
            @RequestParam(value = "username", required = false) String username) {
        return "username";
    }

    @ResponseBody
    @PostMapping("/username")
    public HashMap<String, String> addUser(@RequestParam(value = "username") String username,
            Model model) {
        int smsPassword = otpService.generateOTP(username);
        boolean itRightName = userService.haveLoginInDB(username);
        HashMap<String, String> map = new HashMap<>();
        if (!itRightName) {
            map.put("rightUsername", "false");
            map.put("smsPassword", null);
            map.put("username", "false");
            return map;
        }
//localhost:8080/login?username=22&password=741777  POST

        SmsService.main(otpService.getOtp(username));
        map.put("username", username);
        map.put("smsPassword", String.valueOf(smsPassword));// временно
        this.UserName = username;

        return map; // отдать фронту
//        return "redirect:login"; // для jsp
    }

    // фронту это не надо будет
    @GetMapping("/password")
    public String sendPassword(Model model) {
        // здесь мы ожидаем что к нам придет пароль логин от пользователя
        // мы получаем его и
        SmsService.main(otpService.getOtp(this.UserName));
        model.addAttribute("username", this.UserName);
        return "password";
    }

    // фронту это не надо будет
    // @ResponseBody
    @PostMapping("/password")
    public String checkPassword(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) int password) {
        final String SUCCESS = "Entered Otp is valid";

        final String FAIL = "Entered Otp is NOT valid. Please Retry!";
        // здесь мы ожидаем что к нам c фронта придет пароль логин от пользователя
        // мы получаем его
        // сравниваем с серверным и разрешаем вход(возвращаем на клиента что то и как
        // то)???
        // Проверить Otp

        if (password >= 0) {
            int serverOtp = otpService.getOtp(username);

            if (password == serverOtp) {
                otpService.clearOTP(username);
                System.out.println("пароли совпали и мы удалили из кэша старый отп");

                return "/index"; // ?? как сделать что бы пароль не проверял ??
//                return "{\"rightPassword\":true}";
            } else {
                System.out.println("пароли не совпали");
                return "{\"rightPassword\":false}";
            }
        } else {
            System.out.println("пароль не пришёл");
            return "{\"rightPassword\":false}";
        }
    }

}
