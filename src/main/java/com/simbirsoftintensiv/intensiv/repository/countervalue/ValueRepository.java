package com.simbirsoftintensiv.intensiv.repository.countervalue;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("SELECT cv FROM CounterValue cv WHERE cv.counter=:counter AND cv.dateTime="
            + "(SELECT max(cv.dateTime) FROM CounterValue cv WHERE cv.counter=:counter)")
    CounterValue getLastByCounter(@Param("counter") Counter counter)
            throws DataAccessResourceFailureException;

    List<CounterValue> getByCounter(@Param("counter") Counter counter) throws DataAccessResourceFailureException;

    @Query("SELECT cv FROM CounterValue cv WHERE "
            + "(:type IS NULL OR cv.counter.service.name=:type) AND "
            + "((CAST (:startDate AS date) IS NULL AND CAST(:endDate AS date) IS NULL "
            + "OR cv.dateTime BETWEEN :startDate AND :endDate) OR "
            + "(CAST(:startDate AS date) IS NULL OR cv.dateTime BETWEEN :startDate AND current_date)) AND "
            + "cv.counter IN :counters")
    Page<CounterValue> getByCounters(@Param("counters") List<Counter> counters, @Param("type") String type,
            @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
            Pageable pageable) throws DataAccessResourceFailureException;
}