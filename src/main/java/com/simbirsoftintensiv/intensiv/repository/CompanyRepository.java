package com.simbirsoftintensiv.intensiv.repository;

import com.simbirsoftintensiv.intensiv.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Optional<Company> findById(Integer id);
}
