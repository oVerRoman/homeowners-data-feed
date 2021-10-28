package com.simbirsoftintensiv.intensiv.repository.counter;

import java.util.List;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.simbirsoftintensiv.intensiv.entity.Counter;

public interface CounterRepository extends JpaRepository<Counter, Integer> {

    @Query("SELECT c FROM Counter c WHERE c.user.id=:userId ORDER BY c.id DESC")
    List<Counter> getAll(@Param("userId") int userId) throws DataAccessResourceFailureException;

    @Modifying
    @Transactional
    @Query("DELETE FROM Counter c WHERE c.id=:id AND c.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);
}