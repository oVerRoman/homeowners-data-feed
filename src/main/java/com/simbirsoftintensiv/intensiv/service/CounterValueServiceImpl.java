package com.simbirsoftintensiv.intensiv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.simbirsoftintensiv.intensiv.entity.CounterValue;
import com.simbirsoftintensiv.intensiv.repository.CounterValueRepository;

public class CounterValueServiceImpl implements CounterValueService {

    @Autowired
    CounterValueRepository counterValueRepository;

    @Override
    public void addAllCounterValues(List<CounterValue> values) {
        counterValueRepository.saveAll(values);
    }

    @Override
    public List<CounterValue> getAllCounterValues() {
        return counterValueRepository.findAll();
    }
}