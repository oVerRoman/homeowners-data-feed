package com.simbirsoftintensiv.intensiv.repository;

import com.simbirsoftintensiv.intensiv.entity.Request;
import com.sun.source.doctree.UnknownBlockTagTree;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

    Page<Request> findAllBy(Integer type, String title, Long date, Integer address, String comment, Integer status, Integer clientId, Pageable pageable);

    Long count(Integer type, String title, Long date, Integer address, String comment, Integer status, Integer clientId, Pageable pageable);
}