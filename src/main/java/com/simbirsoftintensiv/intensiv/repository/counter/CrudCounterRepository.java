package com.simbirsoftintensiv.intensiv.repository.counter;

import com.simbirsoftintensiv.intensiv.entity.Counter;

import java.util.List;

public interface CrudCounterRepository {

    Counter save(Counter counter, int userId);


    boolean delete(int id, int userId);


    Counter get(int id, int userId);

    // ORDERED dateTime desc
    List<Counter> getAll(int userId);

    List<Counter> getAll(); //fixme delete

}
