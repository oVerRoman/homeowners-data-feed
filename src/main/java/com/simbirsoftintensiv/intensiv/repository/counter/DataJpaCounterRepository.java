package com.simbirsoftintensiv.intensiv.repository.counter;

import java.util.List;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Repository;

import com.simbirsoftintensiv.intensiv.entity.Counter;
import com.simbirsoftintensiv.intensiv.exception_handling.TimeOutSQLException;
import com.simbirsoftintensiv.intensiv.repository.service.ServiceRepository;
import com.simbirsoftintensiv.intensiv.repository.user.UserRepository;

@Repository
public class DataJpaCounterRepository implements CrudCounterRepository {

    public final CounterRepository counterRepository;
    public final UserRepository userRepository;
    public final ServiceRepository serviceRepository;

    public DataJpaCounterRepository(CounterRepository counterRepository, UserRepository userRepository,
            ServiceRepository serviceRepository) {
        this.counterRepository = counterRepository;
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public Counter get(int id, int userId) {
        return counterRepository.findById(id).filter(counter -> counter.getUser().getId() == userId)
                .orElse(null);
    }

    @Override
    public List<Counter> getAll(int userId) {
        List<Counter> counters;
        try {
            counters = counterRepository.getAll(userId);
        } catch (DataAccessResourceFailureException e) {
            throw new TimeOutSQLException("Соединение прервано. Попробуйте позже.");
        }
        return counters;
    }

    @Override
    public Counter save(Counter counter, int userId) {
        if (!counter.isNew() && get(counter.getId(), userId) == null) {
            return null;
        }
        counter.setUser(userRepository.getById(userId));
        counter.setService(serviceRepository.findByName(counter.getService().getName()));
        try {
            counter = counterRepository.save(counter);
        } catch (DataAccessResourceFailureException e) {
            throw new TimeOutSQLException("Соединение прервано. Попробуйте позже.");
        }
        return counter;
    }

    @Override
    public boolean delete(int id, int userId) {
        return counterRepository.delete(id, userId) == 0;
    }
}