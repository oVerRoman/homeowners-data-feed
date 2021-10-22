package com.simbirsoftintensiv.intensiv.controller;

import com.simbirsoftintensiv.intensiv.AuthorizedUser;
import com.simbirsoftintensiv.intensiv.controller.user.LoginController;
import com.simbirsoftintensiv.intensiv.entity.Counter;
import com.simbirsoftintensiv.intensiv.entity.CounterValue;
import com.simbirsoftintensiv.intensiv.exception_handling.IncorrectCounterValueException;
import com.simbirsoftintensiv.intensiv.exception_handling.RepeatedCounterNameException;
import com.simbirsoftintensiv.intensiv.service.counter.CounterService;
import com.simbirsoftintensiv.intensiv.service.countervalue.ValueService;
import com.simbirsoftintensiv.intensiv.service.user.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@Tag(name = "Counters values controller")
@RequestMapping("/rest")
public class CounterRestController {

    static final Logger log = LoggerFactory.getLogger(CounterRestController.class);
    final CounterService counterService;
    final ValueService valueService;
    final UserService userService;

    public CounterRestController(CounterService counterService, ValueService valueService,
            UserService userService) {
        this.counterService = counterService;
        this.valueService = valueService;
        this.userService = userService;
    }

    @GetMapping("/allcounters") // fixme delete
    public List<Counter> getAllCounters(Authentication authentication) {
        System.out.println(authentication.getPrincipal());
        return counterService.getAll();
    }

    @GetMapping("/counters")
    public List<CounterValue> getAllCounters(@Parameter(hidden = true) @AuthenticationPrincipal AuthorizedUser user) {
        List<Counter> allCounters = counterService.getAll(user.getId());
        List<CounterValue> allValues = valueService.getAll(allCounters);
        return allValues;
    }

    @PostMapping("/counters")
    public Counter addNewCounter(@Parameter(hidden = true) @AuthenticationPrincipal AuthorizedUser user,
            @RequestBody Counter counter) {
        for (Counter counterFromDB : counterService.getAll(user.getId())) {
            if ((counter.getName().trim()).equals(counterFromDB.getName().trim())) {
                log.info("Попытка добавить существующий счетчик " + counter.getName() + ".");
                throw new RepeatedCounterNameException("Счётчик с таким именем уже существует");
            }
        }
        counterService.save(counter, user.getId());
        CounterValue counterValue = new CounterValue();
        counterValue.setCounter(counter);
        counterValue.setValue(0);
        valueService.saveNewValue(counterValue, user.getId(), counter.getId());
        log.info("Пользователь " + user.getUsername() + " добавил новый счетчик " + counter.getName() + ".");
        return counter;
    }

    @PutMapping("/counters")
    public List<CounterValue> addCounterValue(@Parameter(hidden = true) @AuthenticationPrincipal AuthorizedUser user,
            @RequestBody List<CounterValue> counterValues) {
        if (!counterValues.isEmpty()) {
            for (CounterValue counterValue : counterValues) {
                if (counterValue.getValue() > valueService.getLastValue(counterValue.getCounter()).getValue()) {
                    CounterValue newCounterValue = new CounterValue();
                    newCounterValue.setValue(counterValue.getValue());
                    valueService.saveNewValue(newCounterValue, user.getId(), counterValue.getCounter().getId());
                    log.info("Пользователь " + user.getUsername() + " отправил новые значение счетчика.");
                } else if (counterValue.getValue() < valueService.getLastValue(counterValue.getCounter())
                        .getValue()) {
                    log.info("Попытка внести меньшее значение в счетчик пользователем " + user.getUsername() + ".");
                    throw new IncorrectCounterValueException(
                            "Введённое значение " + counterValue.getValue() + " меньше предыдущего");
                }
            }
        }
        return valueService.getAll(counterService.getAll(user.getId()));
    }

    @GetMapping("/counters/history")
    public List<CounterValue> getAllCountersHistory(
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @Parameter(hidden = true) @AuthenticationPrincipal AuthorizedUser user) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        if (startDate != null) {
            startDateTime = LocalDateTime.parse(startDate, formatter);
        }
        if (endDate != null) {
            endDateTime = LocalDateTime.parse(endDate, formatter);
        }
        List<Counter> allCounters = counterService.getAll(user.getId());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").descending());
        List<CounterValue> allValues = valueService
                .getAllHistory(allCounters, type, startDateTime, endDateTime, pageable)
                .getContent();
        return allValues;
    }
}