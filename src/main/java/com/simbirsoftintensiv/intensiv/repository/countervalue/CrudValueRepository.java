package com.simbirsoftintensiv.intensiv.repository.countervalue;

import java.util.List;

import com.simbirsoftintensiv.intensiv.entity.Counter;
import com.simbirsoftintensiv.intensiv.entity.CounterValue;

public interface CrudValueRepository {

    CounterValue save(CounterValue counterValue, int userId, int counterId);

    // false if meal do not belong to userId
    boolean delete(int id, int userId);

    // null if meal do not belong to userId
    CounterValue get(int id, int userId);

    // ORDERED dateTime desc
    List<CounterValue> getAll(int userId);

    CounterValue getLastByCounter(Counter counter);

    List<CounterValue> getByCounter(Counter counter);

    CounterValue saveNewValue(CounterValue value, int userId, int counterId);
}
