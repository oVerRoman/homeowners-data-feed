package com.simbirsoftintensiv.intensiv.controller;

import com.simbirsoftintensiv.intensiv.entity.Request;
import com.simbirsoftintensiv.intensiv.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;

@Controller
@RequestMapping(path = "request")
public class RequestController {
    @Autowired
    private RequestRepository requestRepository;

    // получаем все запросы клиентов
    // ? Как фронт передает дату. В каком формате.
    @GetMapping(path = "")
    public @ResponseBody
    ResponseEntity<List<Request>> getAllRequest(
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "date", required = false) Long date,
            @RequestParam(value = "address", required = false) Integer address,
            @RequestParam(value = "comment", required = false) String comment,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "clientId", required = false) Integer clientId,
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "order", defaultValue = "id") String order){
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(order).descending());
        List<Request> result = requestRepository.findAllBy(type, title, date, address, comment,status, clientId, pageable).getContent();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
