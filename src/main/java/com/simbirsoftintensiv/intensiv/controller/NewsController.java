package com.simbirsoftintensiv.intensiv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;


@Controller
public class NewsController {

    @ResponseBody
    @GetMapping("/news")
    public HashMap<String, HashMap> getNews() {
        HashMap<String, HashMap> map = new HashMap<>();
        HashMap<String, String> news = new HashMap<>();
        news.put("10.10.10", "Внимание жильцы. Сверка показаний счетчиков будет проходить с 00 по **.Просьба быть дома");
        news.put("11.11.21", "Уважаемы жители дома ХХ. Большая просьба быть дома и пускать газовую службу");

        map.put("message", news);

        return map;
    }
}

