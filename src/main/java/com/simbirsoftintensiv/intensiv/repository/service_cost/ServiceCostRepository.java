package com.simbirsoftintensiv.intensiv.repository.service_cost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.simbirsoftintensiv.intensiv.entity.ServiceCost;

public interface ServiceCostRepository extends JpaRepository<ServiceCost, Integer> {

    @Query("SELECT sc FROM ServiceCost sc where sc.service.id=:serviceId AND sc.company.id=:comanyId")
    ServiceCost findByServiceIdAndCompanyId(@Param("serviceId") int serviceId, @Param("companyId") int companyId);
}