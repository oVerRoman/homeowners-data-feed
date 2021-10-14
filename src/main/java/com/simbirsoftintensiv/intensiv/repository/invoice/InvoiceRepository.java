package com.simbirsoftintensiv.intensiv.repository.invoice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.simbirsoftintensiv.intensiv.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Query("SELECT i FROM Invoice i WHERE i.user.id=:userId AND i.date=max(i.date)")
    Invoice findLastByUserIdOrderByDateAsc(@Param("userId") int userId);

    @Query("SELECT i FROM Invoice i WHERE i.user.id=:userId ORDER BY i.id DESC")
    List<Invoice> findAllByUserId(@Param("userId") int userId);
}