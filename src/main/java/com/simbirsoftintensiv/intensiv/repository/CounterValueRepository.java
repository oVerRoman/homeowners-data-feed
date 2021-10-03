package com.simbirsoftintensiv.intensiv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simbirsoftintensiv.intensiv.entity.CounterValue;

public interface CounterValueRepository extends JpaRepository<CounterValue, Integer> {
}