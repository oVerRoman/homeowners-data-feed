package com.simbirsoftintensiv.intensiv.repository.counter;

import com.simbirsoftintensiv.intensiv.entity.Counter;
import com.simbirsoftintensiv.intensiv.repository.user.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataJpaCounterRepository implements CrudCounterRepository{

    public final CounterRepository counterRepository;
    public final UserRepository userRepository;

    public DataJpaCounterRepository(CounterRepository counterRepository, UserRepository userRepository) {
        this.counterRepository = counterRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Counter get(int id, int userId){
        return counterRepository.findById(id).filter(counter -> counter.getUser().getId() == userId)
                .orElse(null);
    }

    @Override
    public List<Counter> getAll(int userId) {
        return counterRepository.getAll(userId);
    }

    @Override
    public Counter save(Counter counter, int userId){
        if (!counter.isNew() && get(counter.getId(), userId) == null) {
            return null;
        }
        counter.setUser(userRepository.getById(userId));
        return counterRepository.save(counter);
    }

    @Override
    public boolean delete(int id, int userId) {
        return counterRepository.delete(id, userId) == 0;
    }

    @Override
    public List<Counter> getAll() {//fixme delete
        return counterRepository.findAll();
    }
}
