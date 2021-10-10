package com.simbirsoftintensiv.intensiv.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simbirsoftintensiv.intensiv.entity.Counter;
import com.simbirsoftintensiv.intensiv.entity.CounterValue;
import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.exception_handling.IncorrectCounterValueException;
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
        if (user == null) {
            throw new NoSuchUserException("Пользователь с телефоном " + userPhone + " не зарегистрирован");
        }
        List<Counter> allCounters = counterService.getAll(user.getId());
        List<CounterValue> allValues = valueService.getAll(allCounters);
        return allValues;
    }

    @PostMapping("/counters")
    public Counter addNewCounter(@RequestBody Counter counter) {
        User user = counter.getUser();
        counterService.save(counter, user.getId());
        CounterValue counterValue = new CounterValue();
        counterValue.setCounter(counter);
        counterValue.setValue(0);
        valueService.saveNewValue(counterValue, user.getId(), counter.getId());
        return counter;
    }

    @PutMapping("/counters")
    public List<CounterValue> addOrUpdateCounterValue(@RequestBody List<CounterValue> counterValues) {
        if (counterValues.size() > 0) {
            User user = counterValues.get(0).getCounter().getUser();
            for (CounterValue counterValue : counterValues) {
                if (counterValue.getValue() >= valueService.get(counterValue.getId(), user.getId())
                        .getValue()) {
                    valueService.saveNewValue(counterValue, user.getId(), counterValue.getCounter().getId());
                } else {
                    throw new IncorrectCounterValueException(
                            "Введённое значение " + counterValue.getValue() + " меньше предыдущего");
                }
            }
        }
        return counterValues;
    }
}