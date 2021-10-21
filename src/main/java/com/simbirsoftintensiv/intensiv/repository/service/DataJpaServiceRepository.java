package com.simbirsoftintensiv.intensiv.repository.service;

import org.springframework.stereotype.Repository;

import com.simbirsoftintensiv.intensiv.entity.Service;

@Repository
public class DataJpaServiceRepository implements CrudServiceRepository {

    public final ServiceRepository serviceRepository;

    public DataJpaServiceRepository(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public Service get(int id) {
        return serviceRepository.findById(id).orElse(null);
    }

    @Override
    public Service getByName(String name) {
        return serviceRepository.findByName(name);
    }

    @Override
    public Service save(Service service) {
        return serviceRepository.save(service);
    }

    @Override
    public void delete(int id) {
        serviceRepository.deleteById(id);
    }
}