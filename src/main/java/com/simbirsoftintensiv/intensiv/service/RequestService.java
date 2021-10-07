package com.simbirsoftintensiv.intensiv.service;

import com.simbirsoftintensiv.intensiv.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public class RequestService {
    @Autowired
    private final RequestRepository requestRepository;


    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

  /*  public long countByParam(Integer type,
                             String title,
                             Long date,
                             Integer address,
                             String comment,
                             Integer status,
                             Integer clientId) {
        return 0;
    }*/


}
