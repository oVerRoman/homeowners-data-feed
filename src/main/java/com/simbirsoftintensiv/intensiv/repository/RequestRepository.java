package com.simbirsoftintensiv.intensiv.repository;


import com.simbirsoftintensiv.intensiv.entity.Request;
import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.repository.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

    Page<Request> findAllBy(Pageable pageable);

    Long countAllBy();

    Request save(Request request, User user);


}