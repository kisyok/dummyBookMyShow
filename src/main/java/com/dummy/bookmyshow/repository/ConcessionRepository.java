package com.dummy.bookmyshow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dummy.bookmyshow.entity.Concession;

@Repository
public interface ConcessionRepository extends JpaRepository<Concession, Long> {
    @Query(value = "select * from concession where concession_id = :concessionId", nativeQuery = true)
    Concession findConcessionById(@Param("concessionId") String concessionId);
}