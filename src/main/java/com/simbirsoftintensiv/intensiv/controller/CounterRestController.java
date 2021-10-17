package com.simbirsoftintensiv.intensiv.controller;

import com.simbirsoftintensiv.intensiv.AuthorizedUser;
import com.simbirsoftintensiv.intensiv.entity.Counter;
import com.simbirsoftintensiv.intensiv.entity.CounterValue;
import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.exception_handling.IncorrectCounterValueException;
import com.simbirsoftintensiv.intensiv.exception_handling.RepeatedCounterNameException;
import com.simbirsoftintensiv.intensiv.service.counter.CounterService;
import com.simbirsoftintensiv.intensiv.service.countervalue.ValueService;
import com.simbirsoftintensiv.intensiv.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class CounterRestController {
    static final Logger log = LoggerFactory.getLogger(LoginController.class);

    final CounterService counterService;
    final ValueService valueService;
    final UserService userService;

    public CounterRestController(CounterService counterService, ValueService valueService,
            UserService userService) {
        this.counterService = counterService;
        this.valueService = valueService;
        this.userService = userService;
    }

    @GetMapping("/allcounters")//fixme delete
    public List<Counter> getAllCounters(Authentication authentication) {
        System.out.println(authentication.getPrincipal());
        return counterService.getAll();
    }

    @GetMapping("/counters")
    public List<CounterValue> getAllCounters(@AuthenticationPrincipal AuthorizedUser user) {
        List<Counter> allCounters = counterService.getAll(user.getId());
        List<CounterValue> allValues = valueService.getAll(allCounters);
        return allValues;
    }

    @PostMapping("/counters")
    public Counter addNewCounter(@RequestBody Counter counter) {
        User user = counter.getUser();
        for (Counter counterFromDB : counterService.getAll(user.getId())) {
            if ((counter.getName().trim()).equals(counterFromDB.getName().trim())) {
                log.info("Попытка добавить существующий счетчик"+ user.getPhone());
                throw new RepeatedCounterNameException("Счётчик с таким именем уже существует");
            }
        }
        counterService.save(counter, user.getId());
        CounterValue counterValue = new CounterValue();
        counterValue.setCounter(counter);
        counterValue.setValue(0);
        valueService.saveNewValue(counterValue, user.getId(), counter.getId());
        log.info("Пользователь "+ user.getPhone() + "добавил новый счетчик" + counter.getName()+" .");
        return counter;
    }

    @PutMapping("/counters")
    public List<CounterValue> addOrUpdateCounterValue(@RequestBody List<CounterValue> counterValues) {
        if (!counterValues.isEmpty()) {
            User user = counterValues.get(0).getCounter().getUser();
            for (CounterValue counterValue : counterValues) {
                if (counterValue.getValue() > valueService.get(counterValue.getId(), user.getId())
                        .getValue()) {
                    valueService.saveNewValue(counterValue, user.getId(), counterValue.getCounter().getId());
                    log.info("Пользователь " + user.getPhone() +  "отправил новые значение счетчика");
                } else if (counterValue.getValue() < valueService.get(counterValue.getId(), user.getId())
                        .getValue()) {
                    log.info("Попытка внести меньшее значение в счетчик "+ user.getPhone());

                    throw new IncorrectCounterValueException(
                            "Введённое значение " + counterValue.getValue() + " меньше предыдущего");
                }
            }
        }
        return counterValues;
    }
}