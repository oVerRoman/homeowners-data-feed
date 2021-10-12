package com.simbirsoftintensiv.intensiv.service.counter;

import java.util.List;

import org.springframework.stereotype.Service;

import com.simbirsoftintensiv.intensiv.entity.Counter;
import com.simbirsoftintensiv.intensiv.repository.counter.CrudCounterRepository;

@Service
public class CounterService {

    final CrudCounterRepository counterRepository;

    public CounterService(CrudCounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    public Counter save(Counter counter, int userId) {
        return counterRepository.save(counter, userId);
    }

    public Counter get(int id, int userId) {
        return counterRepository.get(id, userId);
    }

    public void delete(int id, int userId) {
        counterRepository.delete(id, userId);
    }

    public List<Counter> getAll(int userId) {
        return counterRepository.getAll(userId);
    }

    public List<Counter> getAll() {// fixme delete
        return counterRepository.getAll();
    }
}