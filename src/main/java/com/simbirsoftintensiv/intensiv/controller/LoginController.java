package com.simbirsoftintensiv.intensiv.controller;

import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.service.OtpService;
import com.simbirsoftintensiv.intensiv.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

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
    public String checkUser(Model model,
                            @RequestParam(value = "username", required = false) Integer username
    ) {
        return "username";
    }

    @ResponseBody
    @PostMapping("/onetimecode")
    public HashMap<String, String> getOneTimePassword(@RequestParam(value = "phone") Long phone,
                                           Model model) {

        int oneTimePassword = otpService.generateOTP(phone);

        User user = userService.getByPhone(phone);
//        boolean isRightName = userService.haveLoginInDB(phone);
        HashMap<String, String> map = new HashMap<>();

        if(user == null) {

            map.put("сode", "400");
            map.put("smsPassword", null);
            map.put("username", "false");
        }

//        if (!isRightName) {
//            map.put("сode", "404");
//            map.put("smsPassword", null);
//            map.put("username", "false");
//            return map;
//        }

//localhost:8080/login?username=22&password=741777  POST

//        SmsService.main(otpService.getOtp(phone));
        map.put("username", String.valueOf(phone));
        map.put("Code", "200 OK");
        map.put("smsPassword", String.valueOf(oneTimePassword));// временно

//        this.UserName = phone;


        return map;   // отдать фронту
    }

}
