package com.ioco.discovery.test.atm;

import com.ioco.discovery.test.account.AccountRepository;
import com.ioco.discovery.test.account.AccountTypeCode;
import com.ioco.discovery.test.account.ClientAccount;
import com.ioco.discovery.test.account.CreditCardLimit;
import com.ioco.discovery.test.account.CreditCardLimitRepository;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hjd
 */
@Service
@Slf4j
public class AtmService {

  @Autowired
  private AtmAllocationRepository atmAllocationRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private CreditCardLimitRepository creditCardLimitRepository;

  @Transactional
  public WithdrawResponse withdrawalTransaction(BigDecimal amount, Integer atmId, String accountNumber) {

    List<AtmAllocation> atmAllocations = atmAllocationRepository.findByAtmId(atmId);

    WithdrawResponse withdrawResponse = new WithdrawResponse();

    if (atmAllocations.isEmpty()) {
      withdrawResponse.setMessage("ATM not registered or unfunded");
      return withdrawResponse;
    }

    BigDecimal originalAmount = amount;

    Map<BigDecimal, Integer> noteAllocation = new HashMap();

    boolean allZeros = true;
    for (AtmAllocation atmAllocation : atmAllocations) {
      if (atmAllocation.getCount() > 0) {
        allZeros = false;
        if (atmAllocation.getDenomination().getValue().compareTo(amount) <= 0) {

          //Find number of notes required per denomination
          int numberOfNotes = amount.divideToIntegralValue(atmAllocation.getDenomination().getValue()).intValue();

          //Check if the atm has the correct amount of notes per denomination. If it does not use max and update amount
          if (numberOfNotes > atmAllocation.getCount()) {
            numberOfNotes = atmAllocation.getCount();
            amount = amount.subtract(BigDecimal.valueOf(numberOfNotes).multiply(atmAllocation.getDenomination().getValue()));
          } else {
            amount = amount.remainder(atmAllocation.getDenomination().getValue());
          }

          noteAllocation.put(atmAllocation.getDenomination().getValue(), numberOfNotes);

        }
      }

      //All notes found
      if (amount.compareTo(BigDecimal.ZERO) == 0) {
        break;
      }
    }
    
    if (allZeros) {
      withdrawResponse.setMessage("ATM not registered or unfunded");
      return withdrawResponse;
    }

    withdrawResponse.setNotes(noteAllocation);

    //Check if the ATM has enough money
    if (amount.doubleValue() > 0) {
      withdrawResponse.setMessage("Amount not available, would you like to draw " + withdrawResponse.totalNoteValue());
      withdrawResponse.getNotes().clear();
    } else {
      //Update atm allocation in the database
      atmAllocations.forEach((AtmAllocation atmAllocation) -> {
        Integer value = noteAllocation.get(atmAllocation.getDenomination().getValue());
        if (value != null) {
          atmAllocation.setCount(atmAllocation.getCount() - value);
          atmAllocationRepository.save(atmAllocation);
        }
      });

      //Update account balance
      Optional<ClientAccount> clientAccountOptional = accountRepository.findById(accountNumber);
      if (clientAccountOptional.isPresent()) {
        ClientAccount clientAccount = clientAccountOptional.get();
        clientAccount.setDisplayBalance(clientAccount.getDisplayBalance().subtract(originalAmount));
        accountRepository.saveAndFlush(clientAccount);
      }
    }

    return withdrawResponse;
  }

  public boolean checkAvailableBalance(String clientAccountNumber, AccountTypeCode accountTypeCode, BigDecimal balance, BigDecimal withdrawAmount) {

    boolean hasSufficientFunds = false;

    switch (accountTypeCode) {
      case CHQ:
        hasSufficientFunds = balance.subtract(withdrawAmount).compareTo(new BigDecimal(-10000D)) >= 0;
        break;
      case SVGS:
        hasSufficientFunds = balance.subtract(withdrawAmount).compareTo(BigDecimal.ZERO) >= 0;
        break;
      case PLOAN:
        break;
      case HLOAN:
        break;
      case CCRD:
        hasSufficientFunds = balance.subtract(withdrawAmount).compareTo(BigDecimal.ZERO) >= 0;
        if (hasSufficientFunds == false) {
          Optional<CreditCardLimit> creditCardLimitOptional = creditCardLimitRepository.findById(clientAccountNumber);
          if (creditCardLimitOptional.isPresent()) {
            CreditCardLimit creditCardLimit = creditCardLimitOptional.get();
            hasSufficientFunds = balance.add(creditCardLimit.getAccountLimit()).subtract(withdrawAmount).compareTo(BigDecimal.ZERO) >= 0;
          }
        }
        break;
      case CFCA:
        break;
      default:
        throw new AssertionError(accountTypeCode.name());
    }

    return hasSufficientFunds;
  }
}
