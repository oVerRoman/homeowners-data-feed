package com.simbirsoftintensiv.intensiv.repository;


import com.simbirsoftintensiv.intensiv.entity.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;


@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

    @Query("select r from Request r where "+"" +
            "(:type is null or r.type = :type) and "+
            "(:status is null or r.status = :status) and "+
            "(CAST(:startDate AS date) is null or r.date >= :startDate) and " +
            "(CAST(:endDate AS date) is null or r.date <= :endDate) and " +
            "(:client is null or r.client = :client)")
    Page<Request> findAllBy(@Param("type") Integer type,
                            @Param("status") Integer status,
                            @Param("startDate") LocalDateTime startDate,
                            @Param("endDate")LocalDateTime endDate,
                            @Param("client") Integer client,
                            Pageable pageable);

    @Query("select count(r) from Request r where "+"" +
            "(:type is null or r.type = :type) and "+
            "(:status is null or r.status = :status) and "+"" +
            "(CAST(:startDate as date) is null or r.date >= :startDate) and " +
            "(CAST(:endDate as date) is null or r.date <= :endDate) and " +
            "(:client is null or r.client = :client)")
    Long countAllBy(@Param("type") Integer type,
                    @Param("status") Integer status,
                    @Param("startDate") LocalDateTime startDate,
                    @Param("endDate")LocalDateTime endDate,
                    @Param("client") Integer client);


    @Override
    Request save(Request request);




}