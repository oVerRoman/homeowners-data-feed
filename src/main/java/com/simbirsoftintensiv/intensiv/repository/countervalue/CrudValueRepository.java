package com.simbirsoftintensiv.intensiv.repository.countervalue;

import com.simbirsoftintensiv.intensiv.entity.CounterValue;

import java.util.List;

public interface CrudValueRepository {

    CounterValue save(CounterValue counterValue, int userId, int counterId);

    // false if meal do not belong to userId
    boolean delete(int id, int userId);

    // null if meal do not belong to userId
    CounterValue get(int id, int userId);

    // ORDERED dateTime desc
    List<CounterValue> getAll(int userId);
}
