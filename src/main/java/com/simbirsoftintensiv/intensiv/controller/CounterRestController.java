package com.simbirsoftintensiv.intensiv.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simbirsoftintensiv.intensiv.entity.Counter;
import com.simbirsoftintensiv.intensiv.entity.CounterValue;
import com.simbirsoftintensiv.intensiv.service.counter.CounterService;
import com.simbirsoftintensiv.intensiv.service.countervalue.ValueService;

@RestController
@RequestMapping("/rest")
public class CounterRestController {

    final CounterService counterService;
    final ValueService valueService;

    public CounterRestController(CounterService counterService, ValueService valueService) {
        this.counterService = counterService;
        this.valueService = valueService;
    }

    @GetMapping("/counters")
    public List<CounterValue> getAllCounters() {
        int userId = 100_001;
        List<Counter> allCounters = counterService.getAll(userId);
        List<CounterValue> allValues = valueService.getAll(allCounters);
        return allValues;
    }
}