package com.simbirsoftintensiv.intensiv.service.countervalue;

import java.sql.Date;
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

//    public List<CounterValue> getLastValues(List<Counter> counters) {
//        return repository.getLastByCounters(counters);
//    }

    public void delete(int id, int userId) {
        repository.delete(id, userId);
    }

    public CounterValue save(CounterValue counterValue, int userId, int counterId) {
        return repository.save(counterValue, userId, counterId);
    }

    public List<CounterValue> getAll(List<Counter> counters) {
        List<CounterValue> values = new ArrayList<>();
//        List<CounterValue> values = repository.getLastByCounters(counters);
        CounterValue value;
        for (Counter counter : counters) {
            value = repository.getLastByCounter(counter);
            values.add(value);
        }
        return values;
    }

    public Page<CounterValue> getAllHistory(List<Counter> counters, String type,
            Date startDate, Date endDate, Pageable pageable) {
//        List<CounterValue> values = new ArrayList<>();
        Page<CounterValue> values = repository.getByCounters(counters, type, startDate, endDate, pageable);
//        List<CounterValue> historyValues;
//        for (Counter counter : counters) {
//            historyValues = repository.getByCounter(counter);
//            for (CounterValue historyValue : historyValues) {
//                values.add(historyValue);
//            }
//        }
        return values;
    }

    public CounterValue saveNewValue(CounterValue value, int userId, Integer counterId) {
        return repository.saveNewValue(value, userId, counterId);

    }
}