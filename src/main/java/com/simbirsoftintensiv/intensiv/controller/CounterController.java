package com.simbirsoftintensiv.intensiv.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.simbirsoftintensiv.intensiv.entity.Counter;
import com.simbirsoftintensiv.intensiv.entity.CounterValue;
import com.simbirsoftintensiv.intensiv.entity.CounterValuesList;
import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.service.counter.CounterService;
import com.simbirsoftintensiv.intensiv.service.countervalue.ValueService;

@Controller
public class CounterController {

    final CounterService counterService;
    final ValueService valueService;
//    final UserService userService;

    public CounterController(CounterService counterService, ValueService valueService/*
                                                                                      * , UserService userService
                                                                                      */) {
        this.counterService = counterService;
        this.valueService = valueService;
//        this.userService = userService;
    }

    @GetMapping("/counters")
    public String getAllCounterValues(Model model,
            @ModelAttribute("allCurrentValues") CounterValuesList currentValues,
            @AuthenticationPrincipal User user) {
        // fixme тут нужно присваивать id авторизованного пользователя из
        // спринг-секьюрити
        int userId = 100_001;
        int address_id = 100_000;
//        Address address = userService.get(userId).getAddress();
//        model.addAttribute("address", address);
        List<Counter> counters = counterService.getAll(userId);// fixme
//        List<Counter> counters = counterService.getAll(user.getId());// fixme
        model.addAttribute("allCounters", counters);
        List<CounterValue> counterValuesList = valueService.getAll(counters);
        currentValues.setCounterValues(counterValuesList);
        model.addAttribute("allCounterValues", counterValuesList);
        return "counters";
    }

    @GetMapping("/addCounter")
    public String addCounter(Model model) {
        // fixme тут нужно присваивать id авторизованного пользователя из
        // спринг-секьюрити
        int userId = 100_001;
        model.addAttribute("counter", new Counter());
        return "add-counter";
    }

    @PostMapping("/saveCounter")
    public String saveCounter(@ModelAttribute("counter") Counter counter) {
        // TODO change client_id setting to current client
        // fixme тут нужно присваивать id авторизованного пользователя из
        // спринг-секьюрити
        int userId = 100_001;
        counterService.save(counter, userId);
        return "redirect:/counters";
    }

    @PostMapping("/saveCounterValues")
    public String saveCounterValues(Model model,
            @ModelAttribute("allCurrentValues") CounterValuesList counterValuesList,
            RedirectAttributes redirectAttrs) {
        // To create always new counter values with the same counter_id delete
        // <form:hidden path="counterValues[${status.index}].id"/> from counters.jsp
        int userId = 100_001;
        List<CounterValue> counterValues = counterValuesList.getCounterValues();
        List<Counter> counters = counterService.getAll(userId);
        List<String> errors = new ArrayList<>();
        for (int i = 0; i < counters.size(); i++) {
            if (counterValues.get(i).getValue() != null) {
                if (counterValues.get(i).getValue() != 0) {
                    if (counterValues.get(i).getId() == null
                            || counterValues.get(i).getValue() >= valueService.get(counterValues.get(i).getId(), userId)
                                    .getValue()) {
                        valueService.saveNewValue(counterValues.get(i), userId, counters.get(i).getId());
                        errors.add("");
                    } else {
                        errors.add("Введите значение больше предыдущего");
                    }
                }
            } else {
                errors.add("");
            }
        }
        redirectAttrs.addFlashAttribute("allValueErrors", errors);
        return "redirect:/counters";
    }
}