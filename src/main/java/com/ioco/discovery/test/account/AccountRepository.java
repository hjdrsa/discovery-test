package com.ioco.discovery.test.account;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hjd
 */
@Repository
public interface AccountRepository extends JpaRepository<ClientAccount, String>{
  
  @Query("SELECT c FROM ClientAccount c WHERE client.id = :id ORDER BY displayBalance DESC")
  List<ClientAccount> findbyClientId(@Param("id") Integer id);
  
  @Query("SELECT c FROM ClientAccount c WHERE client.id = :id AND accountType.transactional = :transactional ORDER BY displayBalance DESC")
  List<ClientAccount> findbyClientIdAndTransactional(@Param("id") Integer id, @Param("transactional") Boolean transactional);
  
}
