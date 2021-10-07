package com.simbirsoftintensiv.intensiv.repository;

import com.simbirsoftintensiv.intensiv.entity.Address;
import com.simbirsoftintensiv.intensiv.entity.Request;
import com.simbirsoftintensiv.intensiv.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

  /*  @Query("select r from Request r where"+
            "(:comment is null or r.comment like concat('%',:comment,'%')) and "+
            "(:title is null or r.title like concat('%',:title,'%')) and"+
            "(:type is null or r.type = :type) and"+
            "(:address is null or r.address = :address) and"+
            "(:status is null or r.status = :status) and"+
            "(:clientId is null or r.client = :clientId)") */
    Page<Request> findAllBy(@Param("type") Integer type,
                            @Param("title") String title,
                            @Param("date") Long date,
                            @Param("address") Integer address,
                            @Param("comment") String comment,
                            @Param("status") Integer status,
                            @Param("clientId") Integer clientId,
                            Pageable pageable);

  @Query("select count(r) from Request r where upper(r.type) = upper(:type) and upper(r.title) = upper(:title) and upper(r.date) = upper(:date) and upper(r.address) = upper(:address) and upper(r.comment) = upper(:comment) and upper(r.status) = upper(:status) and upper(r.client) = upper(:client)")
  long countByParam(@Param("type") @Nullable Integer type,
                    @Param("title") @Nullable String title,
                    @Param("date") @Nullable Instant date,
                    @Param("address") @Nullable Address address,
                    @Param("comment") @Nullable String comment,
                    @Param("status") @Nullable Integer status,
                    @Param("client") @Nullable User client);

   /* @Query("select r from Request r where"+
            "(:comment is null or r.comment like concat('%',:comment,'%')) and "+
            "(:title is null or r.title like concat('%',:title,'%')) and"+
            "(:type is null or r.type = :type) and"+
            "(:address is null or r.address = :address) and"+
            "(:status is null or r.status = :status) and"+
            "(:clientId is null or r.client = :clientId)")

    Long countByParam(@Param("type") Integer type,
               @Param("title") String title,
               @Param("date") Long date,
               @Param("address") Integer address,
               @Param("comment") String comment,
               @Param("status") Integer status,
               @Param("clientId") Integer clientId); */

    //long countByTypeEquals(Integer type);


}