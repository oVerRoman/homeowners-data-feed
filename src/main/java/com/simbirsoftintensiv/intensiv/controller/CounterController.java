package com.simbirsoftintensiv.intensiv.controller;

import com.simbirsoftintensiv.intensiv.AuthorizedUser;
import com.simbirsoftintensiv.intensiv.controller.user.LoginController;
import com.simbirsoftintensiv.intensiv.entity.Counter;
import com.simbirsoftintensiv.intensiv.entity.CounterValue;
import com.simbirsoftintensiv.intensiv.entity.CounterValuesList;
import com.simbirsoftintensiv.intensiv.service.counter.CounterService;
import com.simbirsoftintensiv.intensiv.service.countervalue.ValueService;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CounterController {

    static final Logger log = LoggerFactory.getLogger(CounterController.class);
    final CounterService counterService;
    final ValueService valueService;

    public CounterController(CounterService counterService, ValueService valueService) {
        this.counterService = counterService;
        this.valueService = valueService;
    }

    @GetMapping("/counters")
    public String getAllCounterValues(Model model,
            @Valid @ModelAttribute("allCurrentValues") CounterValuesList currentValues,
            @Parameter(hidden = true) @AuthenticationPrincipal AuthorizedUser user) {
        List<Counter> counters = counterService.getAll(user.getId());
        model.addAttribute("allCounters", counters);
        List<CounterValue> counterValuesList = valueService.getAll(counters);
        currentValues.setCounterValues(counterValuesList);
        model.addAttribute("allCounterValues", counterValuesList);
        return "counters";
    }

    @GetMapping("/addCounter")
    public String addCounter(Model model) {
        model.addAttribute("counter", new Counter());
        return "add-counter";
    }

    @PostMapping("/saveCounter")
    public String saveCounter(@ModelAttribute("counter") Counter counter,
            @Parameter(hidden = true) @AuthenticationPrincipal AuthorizedUser user) {
        counterService.save(counter, user.getId());
        return "redirect:/counters";
    }

    @PostMapping("/saveCounterValues")
    public String saveCounterValues(Model model,
            @ModelAttribute("allCurrentValues") CounterValuesList counterValuesList,
            RedirectAttributes redirectAttrs,
            @Parameter(hidden = true) @AuthenticationPrincipal AuthorizedUser user) {
        List<CounterValue> counterValues = counterValuesList.getCounterValues();
        List<Counter> counters = counterService.getAll(user.getId());
        List<String> errors = new ArrayList<>();
        for (int i = 0; i < counters.size(); i++) {
            if (counterValues.get(i).getValue() != null) {
                if (counterValues.get(i).getValue() != 0) {
                    if (counterValues.get(i).getId() == null
                            || counterValues.get(i).getValue() >= valueService
                                    .get(counterValues.get(i).getId().intValue(), user.getId()).getValue()) {
                        valueService.saveNewValue(counterValues.get(i), user.getId(), counters.get(i).getId());
                        errors.add("");
                    } else {
                        errors.add("Введите значение больше предыдущего");
                    }
                } else {
                    errors.add("");
                }
            } else {
                errors.add("");
            }
        }
        redirectAttrs.addFlashAttribute("allValueErrors", errors);
        return "redirect:/counters";
    }

//    @GetMapping("/counters/history")
//    public String getAllCounterValuesHistory(Model model,
//            @Valid @ModelAttribute("allCurrentValues") CounterValuesList currentValues,
//            @Parameter(hidden = true) @AuthenticationPrincipal AuthorizedUser user) {
//        List<Counter> counters = counterService.getAll(user.getId());
//        model.addAttribute("allCounters", counters);
//        List<CounterValue> counterValuesList = valueService.getAllHistory(counters);
//        currentValues.setCounterValues(counterValuesList);
//        model.addAttribute("allCounterValues", counterValuesList);
//        return "counters-history";
//    }
}