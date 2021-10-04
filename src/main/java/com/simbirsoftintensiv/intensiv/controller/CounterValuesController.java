package com.simbirsoftintensiv.intensiv.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

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
    public String showCounterValues(/*
                                     * @RequestParam("counter") counterName, @RequestParam("counterValue")
                                     * counterValueName,
                                     */HttpServletRequest request, Model model, @ModelAttribute Counter counter,
            @ModelAttribute CounterValue counterValue) {
        if (counter == null) {
            model.addAttribute("counter", new Counter());
        }
        if (counterValue == null) {
            model.addAttribute("counterValue", new CounterValue());
        }
        String counterName = request.getParameter("counter");
//        Counter counter = new Counter();
        counter.setName(counterName);
        counter.setClientId(1);
        List<Counter> counters = counterService.getAllCounters();
        counters.add(counter);
        String counterValueName = request.getParameter("counterValue");
//        CounterValue counterValue = new CounterValue();
        counterValue.setCounterId(1);
        counterValue.setValue(Double.parseDouble(counterValueName));
        List<CounterValue> counterValues = counterValueService.getAllCounterValues();
        counterValues.add(counterValue);
        model.addAttribute("allCounters", counters);
        model.addAttribute("allCounterValues", counterValues);
        return "counters";
    }
}