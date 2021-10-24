package com.simbirsoftintensiv.intensiv.repository.countervalue;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.simbirsoftintensiv.intensiv.entity.Counter;
import com.simbirsoftintensiv.intensiv.entity.CounterValue;
import com.simbirsoftintensiv.intensiv.exception_handling.TimeOutSQLException;
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
    public CounterValue getLastByCounter(Counter counter) {
        CounterValue counterValue;
        try {
            counterValue = valueRepository.getLastByCounter(counter);
        } catch (DataAccessResourceFailureException e) {
            throw new TimeOutSQLException("Соединение прервано. Попробуйте позже.");
        }
        return counterValue;
    }

    @Override
    public List<CounterValue> getByCounter(Counter counter) {
        return valueRepository.getByCounter(counter);
    }

    @Override
    public Page<CounterValue> getByCounters(List<Counter> counters, String type,
            LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return valueRepository.getByCounters(counters, type, startDate, endDate, pageable);
    }

    @Override
    public int getAmountByCounters(List<Counter> counters, String type, LocalDateTime startDate,
            LocalDateTime endDate) {
        return valueRepository.getAmountByCounters(counters, type, startDate, endDate);
    }

    @Override
    public CounterValue saveNewValue(CounterValue value, int userId, int counterId) {
        value.setCounter(counterRepository.getById(counterId));
        value.setDateTime(LocalDateTime.now());
        return valueRepository.save(value);
    }
}