package com.simbirsoftintensiv.intensiv.repository.counter;

import java.util.List;

import com.simbirsoftintensiv.intensiv.entity.Counter;

public interface CrudCounterRepository {

    Counter save(Counter counter, int userId);

    boolean delete(int id, int userId);

    Counter get(int id, int userId);

    List<Counter> getAll(int userId);
}