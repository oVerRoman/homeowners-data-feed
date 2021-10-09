package com.simbirsoftintensiv.intensiv.repository.countervalue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.simbirsoftintensiv.intensiv.entity.Counter;
import com.simbirsoftintensiv.intensiv.entity.CounterValue;

public interface ValueRepository extends JpaRepository<CounterValue, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM CounterValue cv WHERE cv.id=:id AND cv.counter.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    CounterValue getByCounter(@Param("mater_id") Counter counter);
}