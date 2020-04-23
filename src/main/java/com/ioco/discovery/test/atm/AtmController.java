package com.ioco.discovery.test.atm;

import com.ioco.discovery.test.account.AccountRepository;
import com.ioco.discovery.test.account.ClientAccount;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hjd
 */
@RestController
@RequestMapping("atms")
public class AtmController {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private AtmService atmService;

  @PutMapping(value = "{atmId}/withdrawal", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(
            summary = "Withdraw cash from an ATM",
            description = "Allow client to withdraw cash from an account",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "SUCCESS",
                        content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {
                                            @ExampleObject(value = "{\"atmId\": 1,\n" +
                                                                  "  \"clientId\": 10,\n" +
                                                                  "  \"accountNumber\": \"4067342946\",\n" +
                                                                  "  \"requiredAmount\": 10}")
                                    })),
                @ApiResponse(
                        responseCode = "400",
                        description = "BAD REQUEST",
                        content = @Content(
                                    examples = {
                                            @ExampleObject(value = "{}")
                                    })),
                @ApiResponse(
                        responseCode = "403",
                        description = "FORBIDDEN",
                        content = @Content(
                                    examples = {
                                            @ExampleObject(value = "{}")
                                    }))
            }
    )
  public ResponseEntity<WithdrawResponse> withdrawCash(@PathVariable int atmId, @RequestBody WithdrawRequest withdrawRequest) {

    if (withdrawRequest.getAtmId() != atmId) {
      return ResponseEntity.badRequest().build();
    }

    WithdrawResponse withdrawResponse = new WithdrawResponse();

    Optional<ClientAccount> clientAccountOptional = accountRepository.findById(withdrawRequest.getAccountNumber());

    if (clientAccountOptional.isPresent() == false) {
      return ResponseEntity.badRequest().build();
    } else {

      ClientAccount clientAccount = clientAccountOptional.get();

      if (clientAccount.getAccountType().getTransactional() == false) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
      }

      if (clientAccount.getClient().getId() != withdrawRequest.getClientId()) {
        return ResponseEntity.badRequest().build();
      }

      boolean hasSufficientFunds = atmService.checkAvailableBalance(clientAccount.getClientAccountNumber(),
              clientAccount.getAccountType().getAccountTypeCode(),
              clientAccount.getDisplayBalance(),
              new BigDecimal(withdrawRequest.getRequiredAmount()));

      if (hasSufficientFunds == false) {
        withdrawResponse.setMessage("Insufficient funds");
      } else {
        withdrawResponse = atmService.withdrawalTransaction(new BigDecimal(withdrawRequest.getRequiredAmount()), atmId, clientAccount.getClientAccountNumber());
      }
    }

    return ResponseEntity.ok().body(withdrawResponse);
  }
}
