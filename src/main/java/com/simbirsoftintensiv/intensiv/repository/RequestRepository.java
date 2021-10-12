package com.simbirsoftintensiv.intensiv.repository;


import com.simbirsoftintensiv.intensiv.entity.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

    @Query("select r from Request r where "+"" +
            "(:status is null or r.status = :status)")
    Page<Request> findAllBy(@Param("status") Integer status, Pageable pageable);

    Long countAllBy();

    Request save(Request request);




}