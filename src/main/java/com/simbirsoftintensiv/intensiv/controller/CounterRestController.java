package com.simbirsoftintensiv.intensiv.controller;

import com.simbirsoftintensiv.intensiv.AuthorizedUser;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@Tag(name = "Counters values controller")
//@CrossOrigin(origins = "https://localhost:3000/", maxAge = 3600, allowCredentials = "true")
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
    public List<CounterValue> getAllCounters1(/*Authentication
    authentication*/@AuthenticationPrincipal AuthorizedUser user) {
//        System.out.println(authentication.getPrincipal());
        List<Counter> allCounters = counterService.getAll();
        List<CounterValue> allValues = valueService.getAll(allCounters);
        return allValues;
//        return counterService.getAll();
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
            @RequestParam(value = "startDate", required = false) String startDateString,
            @RequestParam(value = "endDate", required = false) String endDateString,
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
            @Parameter(hidden = true) @AuthenticationPrincipal AuthorizedUser user) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;
        if (startDateString != null) {
            startDate = LocalDateTime.parse(startDateString, formatter);
        }
        if (endDateString != null) {
            endDate = LocalDateTime.parse(endDateString, formatter);
        }
        List<Counter> allCounters = counterService.getAll(user.getId());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").descending());
        List<CounterValue> allValues = valueService
                .getAllHistory(allCounters, type, startDate, endDate, pageable)
                .getContent();
        return allValues;
    }

    @GetMapping("/counters/history/amount")
    public int getAllCountersHistoryAmount(
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "startDate", required = false) String startDateString,
            @RequestParam(value = "endDate", required = false) String endDateString,
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
            @Parameter(hidden = true) @AuthenticationPrincipal AuthorizedUser user) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;
        if (startDateString != null) {
            startDate = LocalDateTime.parse(startDateString, formatter);
        }
        if (endDateString != null) {
            endDate = LocalDateTime.parse(endDateString, formatter);
        }
        List<Counter> allCounters = counterService.getAll(user.getId());
        int allValuesAmount = valueService.getAllHistoryAmount(allCounters, type, startDate, endDate);
        return allValuesAmount;
    }
}