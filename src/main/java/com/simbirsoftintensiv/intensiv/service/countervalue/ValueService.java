package com.simbirsoftintensiv.intensiv.service.countervalue;


import com.simbirsoftintensiv.intensiv.entity.CounterValue;
import com.simbirsoftintensiv.intensiv.repository.countervalue.CrudValueRepository;
import org.springframework.stereotype.Service;

@Service
public class ValueService {

    final CrudValueRepository repository;

    public ValueService(CrudValueRepository repository) {
        this.repository = repository;
    }

    public CounterValue get(int id, int userId){
        return repository.get(id, userId);
    }

    public void delete(int id, int userId){
        repository.delete(id, userId);
    }

    public CounterValue save(CounterValue counterValue, int userId, int counterId){
        return repository.save(counterValue, userId, counterId);
    }
}