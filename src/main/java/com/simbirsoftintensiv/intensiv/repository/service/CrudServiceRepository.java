package com.simbirsoftintensiv.intensiv.repository.service;

import com.simbirsoftintensiv.intensiv.entity.Service;

public interface CrudServiceRepository {

    Service get(int id);

    Service getByName(String name);

    Service save(Service service);

    void delete(int id);
}