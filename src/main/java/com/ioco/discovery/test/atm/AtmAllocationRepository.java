package com.ioco.discovery.test.atm;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author hjd
 */
public interface AtmAllocationRepository extends JpaRepository<AtmAllocation, Integer>{
  
  @Query("SELECT a FROM AtmAllocation a WHERE atm.id = :atmId ORDER BY denomination.value DESC")
  public List<AtmAllocation> findByAtmId(@Param("atmId") Integer atmId);
}
