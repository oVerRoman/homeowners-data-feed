package com.simbirsoftintensiv.intensiv.repository.countervalue;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.simbirsoftintensiv.intensiv.entity.Counter;
import com.simbirsoftintensiv.intensiv.entity.CounterValue;
import com.simbirsoftintensiv.intensiv.repository.counter.CounterRepository;

@Repository
public class DataJpaValueRepository implements CrudValueRepository {

    final ValueRepository valueRepository;
    final CounterRepository counterRepository;

    public DataJpaValueRepository(ValueRepository valueRepository, CounterRepository counterRepository) {
        this.valueRepository = valueRepository;
        this.counterRepository = counterRepository;
    }

    @Override
    public CounterValue save(CounterValue counterValue, int userId, int counterId) {
        if (!counterValue.isNew() && get(counterValue.getId(), userId) == null) {
            return null;
        }
        counterValue.setCounter(counterRepository.getById(counterId));
        return valueRepository.save(counterValue);
    }

    @Override
    public boolean delete(int id, int userId) {
        return valueRepository.delete(id, userId) != 0;
    }

    @Override
    public CounterValue get(int id, int userId) {
        return valueRepository.findById(id)
                .filter(counterValue -> counterValue.getCounter().getUser().getId() == userId)
                .orElse(null);
    }

    // fixme может, такой метод не нужен?..
    @Override
    public List<CounterValue> getAll(int userId) {
        return null;
    }

    @Override
    public CounterValue getByCounter(Counter counter) {
        return valueRepository.getByCounter(counter);
    }

    @Override
    public CounterValue saveNewValue(CounterValue value, int userId, int counterId) {
        value.setCounter(counterRepository.getById(counterId));
        value.setDateTime(LocalDateTime.now());
        return valueRepository.save(value);
    }
}