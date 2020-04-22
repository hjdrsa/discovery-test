package com.ioco.discovery.test.atm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
/**
 *
 * @author hjd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawRequest {
  
  private Integer atmId;
  private int clientId;
  private String accountNumber;
  private double requiredAmount;
  
}
