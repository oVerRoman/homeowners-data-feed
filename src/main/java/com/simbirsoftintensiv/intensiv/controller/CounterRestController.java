package com.simbirsoftintensiv.intensiv.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simbirsoftintensiv.intensiv.entity.Counter;
import com.simbirsoftintensiv.intensiv.entity.CounterValue;
import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.exception_handling.NoSuchUserException;
import com.simbirsoftintensiv.intensiv.service.counter.CounterService;
import com.simbirsoftintensiv.intensiv.service.countervalue.ValueService;
import com.simbirsoftintensiv.intensiv.service.user.UserService;

@RestController
@RequestMapping("/rest")
public class CounterRestController {

    final CounterService counterService;
    final ValueService valueService;
    final UserService userService;

    public CounterRestController(CounterService counterService, ValueService valueService,
            UserService userService) {
        this.counterService = counterService;
        this.valueService = valueService;
        this.userService = userService;
    }

    @GetMapping("/counters/{userPhone}")
    public List<CounterValue> getAllCounters(@PathVariable String userPhone) {
        User user = (User) userService.loadUserByUsername(userPhone);
        System.out.println(user);
        if (user == null) {
            throw new NoSuchUserException("Пользователь с телефоном " + userPhone + " не зарегистрирован");
        }
        int userId = user.getId();
        List<Counter> allCounters = counterService.getAll(userId);
        List<CounterValue> allValues = valueService.getAll(allCounters);
        return allValues;
    }
}