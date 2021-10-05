package com.simbirsoftintensiv.intensiv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.simbirsoftintensiv.intensiv.entity.Counter;
import com.simbirsoftintensiv.intensiv.entity.CounterValue;
import com.simbirsoftintensiv.intensiv.service.CounterService;
import com.simbirsoftintensiv.intensiv.service.CounterValueService;

@Controller
public class CounterValuesController {

    @Autowired
    CounterService counterService;
    @Autowired
    CounterValueService counterValueService;

    @GetMapping("/counters")
    public String getAllCounterValues(Model model) {
        List<Counter> counters = counterService.getAllCounters();
        model.addAttribute("counter", new Counter());
        model.addAttribute("allCounters", counters);
        List<CounterValue> counterValues = counterValueService.getAllCounterValues();
        model.addAttribute("allCounterValues", counterValues);
        return "counters";
    }

    @GetMapping("/addCounter")
    public String addCounter(Model model) {
        model.addAttribute("counter", new Counter());
        return "add-counter";
    }

    @PostMapping("/saveCounter")
    public String saveCounter(@ModelAttribute("counter") Counter counter) {
        // TODO change client_id setting to current client
        counter.setClientId(100003);
        counterService.addCounter(counter);
        return "redirect:/counters";
    }

    @PostMapping("/saveCounterValues")
    public String saveCounterValues(@ModelAttribute("counterValue") CounterValue counterValue) {
        // TODO save all counter values
        return "redirect:/counters";
    }
}