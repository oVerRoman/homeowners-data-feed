package com.simbirsoftintensiv.intensiv.service;

import java.util.List;

import com.simbirsoftintensiv.intensiv.entity.Counter;

public interface CounterService {

    public void addCounter(Counter counter);

    public List<Counter> getAllCounters();
}