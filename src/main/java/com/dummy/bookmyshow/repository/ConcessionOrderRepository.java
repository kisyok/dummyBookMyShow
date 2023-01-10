package com.dummy.bookmyshow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dummy.bookmyshow.entity.Concession;
import com.dummy.bookmyshow.entity.ConcessionOrder;

@Repository
public interface ConcessionOrderRepository extends JpaRepository<ConcessionOrder, Long> {
    @Query(value = "select * from concession_order where concession_order_id = :concessionOrderId", nativeQuery = true)
    ConcessionOrder findConcessionOrderById(@Param("concessionOrderId") String concessionOrderId);
}