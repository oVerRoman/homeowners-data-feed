package com.simbirsoftintensiv.intensiv.repository.countervalue;

import com.simbirsoftintensiv.intensiv.entity.CounterValue;
import com.simbirsoftintensiv.intensiv.repository.counter.CounterRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        return valueRepository.findById(id).filter(counterValue ->
                counterValue.getCounter().getUser().getId() == userId)
                .orElse(null);
    }

    //fixme может, такой метод не нужен?..
    @Override
    public List<CounterValue> getAll(int userId) {
        return null;
    }
}
