package com.simbirsoftintensiv.intensiv.controller;

import com.simbirsoftintensiv.intensiv.entity.Counter;
import com.simbirsoftintensiv.intensiv.entity.CounterValue;
import com.simbirsoftintensiv.intensiv.service.counter.CounterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CounterController {

    final CounterService counterService;

    public CounterController(CounterService counterService) {
        this.counterService = counterService;
    }

    @GetMapping("/counters")
    public String getAllCounterValues(Model model) {
        //fixme тут нужно присваивать id авторизованного пользователя из спринг-секьюрити
        int userId = 1_000_000;
        List<Counter> counters = counterService.getAll(userId);//fixme
//        model.addAttribute("counter", new Counter());
        model.addAttribute("allCounters", counters);
//        закомментировал, потому что у allCounters будут values
//        List<CounterValue> counterValues = counterValueService.getAllCounterValues();
//        model.addAttribute("allCounterValues", counterValues);
        return "counters";
    }

    @GetMapping("/addCounter")
    public String addCounter(Model model) {
        //fixme тут нужно присваивать id авторизованного пользователя из спринг-секьюрити
        int userId = 1_000_000;
        model.addAttribute("counter", new Counter());
        return "add-counter";
    }

    @PostMapping("/saveCounter")
    public String saveCounter(@ModelAttribute("counter") Counter counter) {
        // TODO change client_id setting to current client
        //fixme тут нужно присваивать id авторизованного пользователя из спринг-секьюрити
        int userId = 1_000_000;
        //counter.setClientId(100003);
        counterService.save(counter, userId);
        return "redirect:/counters";
    }

    @PostMapping("/saveCounterValues")
    public String saveCounterValues(@ModelAttribute("counterValue") CounterValue counterValue) {
        // TODO save all counter values
        return "redirect:/counters";
    }
}