package com.simbirsoftintensiv.intensiv.service.countervalue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.simbirsoftintensiv.intensiv.entity.Counter;
import com.simbirsoftintensiv.intensiv.entity.CounterValue;
import com.simbirsoftintensiv.intensiv.repository.countervalue.CrudValueRepository;

@Service
public class ValueService {

    final CrudValueRepository repository;

    public ValueService(CrudValueRepository repository) {
        this.repository = repository;
    }

    public CounterValue get(int id, int userId) {
        return repository.get(id, userId);
    }

    public CounterValue getLastValue(Counter counter) {
        return repository.getLastByCounter(counter);
    }

    public void delete(int id, int userId) {
        repository.delete(id, userId);
    }

    public CounterValue save(CounterValue counterValue, int userId, int counterId) {
        return repository.save(counterValue, userId, counterId);
    }

    public List<CounterValue> getAll(List<Counter> counters) {
        List<CounterValue> values = new ArrayList<>();
        CounterValue value;
        for (Counter counter : counters) {
            value = repository.getLastByCounter(counter);
            values.add(value);
        }
        return values;
    }

    public Page<CounterValue> getAllHistory(List<Counter> counters, String type,
            LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return repository.getByCounters(counters, type, startDate, endDate, pageable);
    }

    public CounterValue saveNewValue(CounterValue value, int userId, Integer counterId) {
        return repository.saveNewValue(value, userId, counterId);

    }
}