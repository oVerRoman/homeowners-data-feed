package com.simbirsoftintensiv.intensiv.repository;

import com.simbirsoftintensiv.intensiv.entity.Request;
import com.sun.source.doctree.UnknownBlockTagTree;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

    @Query("select r from Request r where"+
            "(:comment is null or r.comment like concat('%',:comment,'%')) and "+
            "(:title is null or r.title like concat('%',:title,'%')) and"+
            "(:type is null or r.type = :type) and"+
            "(:address is null or r.address = :address) and"+
            "(:status is null or r.status = :status) and"+
            "(:clientId is null or r.client = :clientId)")
    Page<Request> findAllBy(@Param("type") Integer type,
                            @Param("title") String title,
                            @Param("date") Long date,
                            @Param("address") Integer address,
                            @Param("comment") String comment,
                            @Param("status") Integer status,
                            @Param("clientId") Integer clientId,
                            Pageable pageable);

    @Query("select r from Request r where"+
            "(:comment is null or r.comment like concat('%',:comment,'%')) and "+
            "(:title is null or r.title like concat('%',:title,'%')) and"+
            "(:type is null or r.type = :type) and"+
            "(:address is null or r.address = :address) and"+
            "(:status is null or r.status = :status) and"+
            "(:clientId is null or r.client = :clientId)")
    Long count(@Param("type") Integer type,
               @Param("title") String title,
               @Param("date") Long date,
               @Param("address") Integer address,
               @Param("comment") String comment,
               @Param("status") Integer status,
               @Param("clientId") Integer clientId);
}