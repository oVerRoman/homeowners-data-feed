package com.simbirsoftintensiv.intensiv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simbirsoftintensiv.intensiv.entity.Counter;
import com.simbirsoftintensiv.intensiv.repository.CounterRepository;

@Service
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