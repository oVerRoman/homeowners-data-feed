package com.simbirsoftintensiv.intensiv.service.counter;

import com.simbirsoftintensiv.intensiv.entity.Counter;
import com.simbirsoftintensiv.intensiv.repository.counter.CrudCounterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CounterService {

    final CrudCounterRepository counterRepository;

    public CounterService(CrudCounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    public Counter save(Counter counter, int userId) {
        return counterRepository.save(counter, userId);
    }

    public Counter get(int id, int userId){
        return counterRepository.get(id, userId);
    }

    public void delete(int id, int userId) { counterRepository.delete(id, userId); }

    public List<Counter> getAll(int userId) {
        return counterRepository.getAll(userId);
    }
}