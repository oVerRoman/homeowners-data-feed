package com.simbirsoftintensiv.intensiv.controller;

import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.exception_handling.NoSuchUserException;
import com.simbirsoftintensiv.intensiv.service.OtpService;
import com.simbirsoftintensiv.intensiv.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class LoginController {
    static final Logger log =
            LoggerFactory.getLogger(LoginController.class);
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
    @PostMapping("/onetimecode")
    public HashMap<String, String> getOneTimePassword(@RequestParam(value = "username") Long phone) {
        log.info("Authorization attempt " + phone + ".");
        int oneTimePassword = otpService.generateOTP(phone);

        User user = userService.getByPhone(phone);
        HashMap<String, String> map = new HashMap<>();

        if (user == null) {
            log.warn("User " + phone + " not find. ");
            throw new NoSuchUserException("User " + phone + " not find. ");
        }

        map.put("username", String.valueOf(phone));
        map.put("smsPassword", String.valueOf(oneTimePassword));// временно
        return map;
    }

}
