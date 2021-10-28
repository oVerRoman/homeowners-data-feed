package com.simbirsoftintensiv.intensiv.repository.countervalue;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.simbirsoftintensiv.intensiv.entity.Counter;
import com.simbirsoftintensiv.intensiv.entity.CounterValue;

public interface CrudValueRepository {

    CounterValue save(CounterValue counterValue, int userId, int counterId);

    boolean delete(int id, int userId);

    CounterValue get(int id, int userId);

    CounterValue getLastByCounter(Counter counter);

    List<CounterValue> getByCounter(Counter counter);

    Page<CounterValue> getByCounters(List<Counter> counters, String type,
            LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    int getAmountByCounters(List<Counter> counters, String type,
            LocalDateTime startDate, LocalDateTime endDate);

    CounterValue saveNewValue(CounterValue value, int userId, int counterId);
}