package com.simbirsoftintensiv.intensiv.repository.counter;

import com.simbirsoftintensiv.intensiv.entity.Counter;

import java.util.List;

public interface CrudCounterRepository {

    Counter save(Counter counter, int userId);

    // false if meal do not belong to userId
    boolean delete(int id, int userId);

    // null if meal do not belong to userId
    Counter get(int id, int userId);

    // ORDERED dateTime desc
    List<Counter> getAll(int userId);

}
