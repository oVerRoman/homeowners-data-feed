package com.simbirsoftintensiv.intensiv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simbirsoftintensiv.intensiv.entity.Counter;

public interface CounterRepository extends JpaRepository<Counter, Integer> {
}