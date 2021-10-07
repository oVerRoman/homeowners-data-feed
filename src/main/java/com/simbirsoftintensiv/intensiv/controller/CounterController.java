package com.simbirsoftintensiv.intensiv.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.simbirsoftintensiv.intensiv.entity.Counter;
import com.simbirsoftintensiv.intensiv.entity.CounterValue;
import com.simbirsoftintensiv.intensiv.entity.CounterValuesList;
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
            @ModelAttribute("allCurrentValues") CounterValuesList currentValues) {
        // fixme тут нужно присваивать id авторизованного пользователя из
        // спринг-секьюрити
        int userId = 100_000;
        List<Counter> counters = counterService.getAll(userId);// fixme
//        model.addAttribute("counter", new Counter());
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
        int userId = 100_000;
        model.addAttribute("counter", new Counter());
        return "add-counter";
    }

    @PostMapping("/saveCounter")
    public String saveCounter(@ModelAttribute("counter") Counter counter) {
        // TODO change client_id setting to current client
        // fixme тут нужно присваивать id авторизованного пользователя из
        // спринг-секьюрити
        int userId = 100_000;
        counterService.save(counter, userId);
        return "redirect:/counters";
    }

    @PostMapping("/saveCounterValues")
    public String saveCounterValues(Model model,
            @ModelAttribute("allCurrentValues") CounterValuesList counterValuesList) {
        // To create always new counter values with the same counter_id delete
        // <form:hidden path="counterValues[${status.index}].id"/> from counters.jsp
        int userId = 100_000;
        List<CounterValue> counterValues = counterValuesList.getCounterValues();
        List<Counter> counters = counterService.getAll(userId);
        for (int i = 0; i < counters.size(); i++) {
            if (counterValues.get(i).getValue() != null) {
                if (counterValues.get(i).getValue() != 0) {
                    valueService.saveNewValue(counterValues.get(i), userId, counters.get(i).getId());
                }
            }
        }
        return "redirect:/counters";
    }
}