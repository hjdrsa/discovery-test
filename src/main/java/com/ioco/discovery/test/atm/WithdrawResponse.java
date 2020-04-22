package com.ioco.discovery.test.atm;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author hjd
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WithdrawResponse {
  
  private String message;
  private Map<BigDecimal,Integer> notes = new HashMap();
  private BigDecimal oldBalance;
  private BigDecimal newBalance;
  
  public BigDecimal totalNoteValue(){
    
    BigDecimal noteValue = BigDecimal.ZERO;
    
    for (Map.Entry<BigDecimal, Integer> entry : notes.entrySet()) {
      BigDecimal key = entry.getKey();
      Integer value = entry.getValue();
      noteValue = noteValue.add(key.multiply(BigDecimal.valueOf(value)));
    }
    
    return noteValue;
  }
}
