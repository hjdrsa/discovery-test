package com.ioco.discovery.test.account;

import com.fasterxml.jackson.annotation.JsonView;
import com.ioco.discovery.test.View;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("clients/{clientId}/accounts")
public class AccountController {
  
  @Autowired
  private AccountRepository accountRepository;
  
  @JsonView(View.List.class)
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(
            summary = "Display account balances",
            description = "To view available balances",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "SUCCESS")
            }
    )
  public ResponseEntity<List<ClientAccount>> adminPage(@PathVariable Integer clientId, @RequestParam(value = "transactional", required = false) Boolean transactional, @RequestParam(value = "accountTypeCode", required = false) AccountTypeCode accountTypeCode) {
    
    List<ClientAccount> clientAccounts = null;
    
    if (transactional != null && accountTypeCode != null) {
      clientAccounts = accountRepository.findbyClientIdAndTransactionalAndAccountTypeCode(clientId, transactional, accountTypeCode);
    } else if (transactional != null) {
      clientAccounts = accountRepository.findbyClientIdAndTransactional(clientId, transactional);
    } else if (accountTypeCode != null) {
      clientAccounts = accountRepository.findbyClientIdAndAccountTypeCode(clientId, accountTypeCode);
    } else if (transactional == null) {
      clientAccounts = accountRepository.findbyClientId(clientId);
    } 
    
    return ResponseEntity.ok(clientAccounts);
  }
}
