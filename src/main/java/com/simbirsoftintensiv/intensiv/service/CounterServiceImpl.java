package com.simbirsoftintensiv.intensiv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.simbirsoftintensiv.intensiv.entity.Counter;
import com.simbirsoftintensiv.intensiv.repository.CounterRepository;

public class CounterServiceImpl implements CounterService {

    @Autowired
    CounterRepository counterRepository;

    @Override
    public void addCounter(Counter counter) {
        counterRepository.save(counter);
    }

    @Override
    public List<Counter> getAllCounters() {
        return counterRepository.findAll();
    }
}