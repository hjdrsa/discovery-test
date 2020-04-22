package com.ioco.discovery.test.atm;

import com.ioco.discovery.test.account.AccountTypeCode;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author hjd
 */
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class AtmServiceTest {
  
  @Autowired
  AtmService atmService;

  @Test
  public void test1Withdrawel550() {
    
    WithdrawResponse withdrawResponse = atmService.withdrawalTransaction(new BigDecimal(550L), 1, "40241836111");
    Map<BigDecimal, Integer> noteAllocation = withdrawResponse.getNotes();
    
    Assert.assertEquals(3, noteAllocation.size());
    
    noteAllocation.entrySet().forEach((Map.Entry<BigDecimal, Integer> entry) -> {
      BigDecimal denomination = entry.getKey();
      int value = entry.getValue();
      switch (denomination.intValue()) {
        case 200:
          Assert.assertEquals(2L, value);
          break;
        case 100:
          Assert.assertEquals(1L, value);
          break;
        case 50:
          Assert.assertEquals(1L, value);
          break;
        default:
          break;
      }
    });
  }
  
  @Test
  public void test2Withdrawel600() {
    
    WithdrawResponse withdrawResponse = atmService.withdrawalTransaction(new BigDecimal(600L), 1, "40241836111");
    Map<BigDecimal, Integer> noteAllocation = withdrawResponse.getNotes();
    
    Assert.assertEquals(4, noteAllocation.size());
    
    noteAllocation.entrySet().forEach((Map.Entry<BigDecimal, Integer> entry) -> {
      BigDecimal denomination = entry.getKey();
      int value = entry.getValue();
      switch (denomination.intValue()) {
        case 100:
          Assert.assertEquals(2L, value);
          break;
        case 50:
          Assert.assertEquals(3L, value);
          break;
        case 20:
          Assert.assertEquals(5L, value);
          break;
        case 10:
          Assert.assertEquals(15L, value);
          break;
        default:
          break;
      }
    });
  }
  
  @Test
  public void test3Withdrawe350() {
    
    WithdrawResponse withdrawResponse = atmService.withdrawalTransaction(new BigDecimal(350L), 1, "40241836111");
    Map<BigDecimal, Integer> noteAllocation = withdrawResponse.getNotes();
    
    Assert.assertEquals(1, noteAllocation.size());
    
    noteAllocation.entrySet().forEach((Map.Entry<BigDecimal, Integer> entry) -> {
      BigDecimal denomination = entry.getKey();
      int value = entry.getValue();
      switch (denomination.intValue()) {
        case 10:
          Assert.assertEquals(35L, value);
          break;
        default:
          break;
      }
    });
  }
  
  @Test
  public void test4WithdraweAtmEmpty() {
    
    WithdrawResponse withdrawResponse = atmService.withdrawalTransaction(new BigDecimal(350L), 1, "40241836111");
    Map<BigDecimal, Integer> noteAllocation = withdrawResponse.getNotes();
    
    Assert.assertEquals(0, noteAllocation.size());
  }
  
  @Test
  public void test5CheckAvailableBalanceCreditCardValid() {
    
    boolean hasSufficientFunds = atmService.checkAvailableBalance("5027913218", AccountTypeCode.CCRD, BigDecimal.ZERO, BigDecimal.valueOf(25000.000));
    
    Assert.assertTrue(hasSufficientFunds);
  }
  
  @Test
  public void test6CheckAvailableBalanceCreditCardValid() {
    
    boolean hasSufficientFunds = atmService.checkAvailableBalance("5027913218", AccountTypeCode.CCRD, BigDecimal.valueOf(-10000), BigDecimal.valueOf(15000.000));
    
    Assert.assertTrue(hasSufficientFunds);
  }
  
  @Test
  public void test7CheckAvailableBalanceCreditCardInValid() {
    
    boolean hasSufficientFunds = atmService.checkAvailableBalance("5027913218", AccountTypeCode.CCRD, BigDecimal.ZERO, BigDecimal.valueOf(25001.000));
    
    Assert.assertFalse(hasSufficientFunds);
  }
  
  @Test
  public void test8CheckAvailableBalanceCreditCardInValid() {
    
    boolean hasSufficientFunds = atmService.checkAvailableBalance("5027913218", AccountTypeCode.CCRD, BigDecimal.valueOf(-10000), BigDecimal.valueOf(15001.000));
    
    Assert.assertFalse(hasSufficientFunds);
  }
  
  @Test
  public void test9CheckAvailableBalanceChequeValid() {
    
    boolean hasSufficientFunds = atmService.checkAvailableBalance(null, AccountTypeCode.CHQ, BigDecimal.ZERO, BigDecimal.valueOf(10000.00));
    
    Assert.assertTrue(hasSufficientFunds);
  }
  
  @Test
  public void test10CheckAvailableBalanceChequeValid() {
    
    boolean hasSufficientFunds = atmService.checkAvailableBalance(null, AccountTypeCode.CHQ, BigDecimal.valueOf(10000), BigDecimal.valueOf(20000.00));
    
    Assert.assertTrue(hasSufficientFunds);
  }
  
  @Test
  public void test11CheckAvailableBalanceChequeInValid() {
    
    boolean hasSufficientFunds = atmService.checkAvailableBalance(null, AccountTypeCode.CHQ, BigDecimal.ZERO, BigDecimal.valueOf(10000.01));
    
    Assert.assertFalse(hasSufficientFunds);
  }
  
  @Test
  public void test12CheckAvailableBalanceChequeInValid() {
    
    boolean hasSufficientFunds = atmService.checkAvailableBalance(null, AccountTypeCode.CHQ, BigDecimal.valueOf(10000), BigDecimal.valueOf(20000.01));
    
    Assert.assertFalse(hasSufficientFunds);
  }
  
  @Test
  public void test13CheckAvailableBalanceHomeLoan() {
    
    boolean hasSufficientFunds = atmService.checkAvailableBalance(null, AccountTypeCode.HLOAN, BigDecimal.ZERO, BigDecimal.valueOf(10000.01));
    
    Assert.assertFalse(hasSufficientFunds);
  }
  
  @Test
  public void test14CheckAvailableBalancePersonalLoan() {
    
    boolean hasSufficientFunds = atmService.checkAvailableBalance(null, AccountTypeCode.PLOAN, BigDecimal.valueOf(10000), BigDecimal.valueOf(20000.01));
    
    Assert.assertFalse(hasSufficientFunds);
  }
  
  @Test
  public void test15CheckAvailableBalanceForeignCurrency() {
    
    boolean hasSufficientFunds = atmService.checkAvailableBalance(null, AccountTypeCode.CFCA, BigDecimal.valueOf(10000), BigDecimal.valueOf(20000.01));
    
    Assert.assertFalse(hasSufficientFunds);
  }
}
