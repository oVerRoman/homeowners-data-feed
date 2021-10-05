package com.simbirsoftintensiv.intensiv.controller;

import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.service.OtpService;
import com.simbirsoftintensiv.intensiv.service.SmsService;
import com.simbirsoftintensiv.intensiv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class LoginController {
    private String UserName;
    @Autowired
    private UserService userService; // внедряем обьект

    @Autowired
    public OtpService otpService;



    // фронту это не надо будет
    @GetMapping("/username")
    public String checkUser(Model model,
                            @RequestParam(value = "username", required = false) String username
    ) {
        System.out.println("тут только вводим имя");

        return "username";
    }
//    @ResponseBody
    @PostMapping("/username")
    public String addUser(@RequestParam(value = "username", required = false) String username,
                          Model model) {
        // генерируем новый пароль
        otpService.generateOTP(username);


        if (!userService.haveLoginInDB(username)) {
            System.out.println("if (userService.checkLogin(username)) " + username );
            // фронту отдать json где будет написано {descrition?: "Неправильный логин..."
            // или как  ??
            model.addAttribute("usernameError", "Неправильный логин...");
            return "{\"rightUsername\":false}";
        }

        this.UserName = username;

        model.addAttribute("username", username);

        // отдать фронту return  "{\"rightUsername\":true}";
        return "redirect:password";
    }

    // фронту это не надо будет
    @GetMapping("/password")
    public String sendPassword(Model model) {
        // здесь мы ожидаем что к нам придет пароль  логин от пользователя
        // мы получаем его и
        SmsService.main(otpService.getOtp(this.UserName));
        model.addAttribute("username", this.UserName);
        return "password";
    }
    @ResponseBody
    @PostMapping("/password")
    public String checkPassword (
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) int password
    ){
        final String SUCCESS = "Entered Otp is valid";

        final String FAIL = "Entered Otp is NOT valid. Please Retry!";
        // здесь мы ожидаем что к нам c фронта придет пароль  логин от пользователя
        // мы получаем его
        // сравниваем с серверным и разрешаем вход(возвращаем на клиента что то и как то)???
        //Проверить Otp

        if(password >= 0){
            int serverOtp = otpService.getOtp(username);

                if(password == serverOtp){
                    otpService.clearOTP(username);
                    System.out.println("пароли совпали и мы удалили из кэша старый отп");
                    //TODO как сделать так что бы на этом моменте от авторизовывался
                    //   но у меня не получилось :((
//                    return "/index"; // ?? как сделать что бы пароль не проверял ??
                    return "{\"rightPassword\":true}";
                }else{
                    System.out.println("пароли не совпали");
                    return "{\"rightPassword\":false}";
                }
            }else {
            System.out.println("пароль не пришёл");
            return "{\"rightPassword\":false}";
            }
        }

    }


