package com.simbirsoftintensiv.intensiv.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

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

    public CounterController(CounterService counterService, ValueService valueService) {
        this.counterService = counterService;
        this.valueService = valueService;
    }

    @GetMapping("/counters")
    public String getAllCounterValues(Model model,
            @Valid @ModelAttribute("allCurrentValues") CounterValuesList currentValues,
            @AuthenticationPrincipal User user) {
        // fixme тут нужно присваивать id авторизованного пользователя из
        // спринг-секьюрити
        int userId = 60001;
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
    public String addCounter(Model model,
            @AuthenticationPrincipal User user) {
        // fixme тут нужно присваивать id авторизованного пользователя из
        // спринг-секьюрити
        int userId = 60001;
        model.addAttribute("counter", new Counter());
        return "add-counter";
    }

    @PostMapping("/saveCounter")
    public String saveCounter(@ModelAttribute("counter") Counter counter,
            @AuthenticationPrincipal User user) {
        // fixme тут нужно присваивать id авторизованного пользователя из
        // спринг-секьюрити
        int userId = 60001;
        counterService.save(counter, userId);
        return "redirect:/counters";
    }

    @PostMapping("/saveCounterValues")
    public String saveCounterValues(Model model,
            @ModelAttribute("allCurrentValues") CounterValuesList counterValuesList,
            RedirectAttributes redirectAttrs, @AuthenticationPrincipal User user) {
        // To create always new counter values with the same counter_id delete
        // <form:hidden path="counterValues[${status.index}].id"/> from counters.jsp
        int userId = 60001;
        List<CounterValue> counterValues = counterValuesList.getCounterValues();
        List<Counter> counters = counterService.getAll(userId);
        List<String> errors = new ArrayList<>();
        for (int i = 0; i < counters.size(); i++) {
            if (counterValues.get(i).getValue() != null) {
                if (counterValues.get(i).getValue() != 0) {
                    if (counterValues.get(i).getId() == null
                            || counterValues.get(i).getValue() >= valueService
                                    .get(counterValues.get(i).getId().intValue(), userId).getValue()) {
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