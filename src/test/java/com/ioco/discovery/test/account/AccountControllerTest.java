package com.ioco.discovery.test.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.RoundingMode;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 *
 * @author hjd
 */
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void test1FindAccountsByClientId() throws Exception {
    String jsonResponse = mvc.perform(MockMvcRequestBuilders
            .get("/clients/{clientId}/accounts", 1)
            .contentType(MediaType.APPLICATION_JSON)
    )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    ClientAccount[] clientAccounts = objectMapper.readValue(jsonResponse, ClientAccount[].class);
    Assert.assertEquals(8, clientAccounts.length);
  }

  @Test
  public void test2FindAccountsByClientIdAndTransactional() throws Exception {
    String jsonResponse = mvc.perform(MockMvcRequestBuilders
            .get("/clients/{clientId}/accounts?transactional=true", 1)
            .contentType(MediaType.APPLICATION_JSON)
    )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    ClientAccount[] clientAccounts = objectMapper.readValue(jsonResponse, ClientAccount[].class);
    Assert.assertEquals(3, clientAccounts.length);

    for (ClientAccount clientAccount : clientAccounts) {
      Assert.assertTrue(clientAccount.getAccountType().getTransactional());

      if ("/".equals(clientAccount.getCurrency().getCurrencyConversionRate().getCurrencyCode())) {
        Assert.assertEquals(clientAccount.getDisplayBalance().setScale(clientAccount.getCurrency().getDecimalPlaces()),
                clientAccount.getForexBalance().divide(clientAccount.getCurrency().getCurrencyConversionRate().getRate(),
                        clientAccount.getCurrency().getDecimalPlaces(), RoundingMode.HALF_UP));
      } else {
        Assert.assertEquals(clientAccount.getDisplayBalance().setScale(clientAccount.getCurrency().getDecimalPlaces()),
                clientAccount.getForexBalance().multiply(clientAccount.getCurrency().getCurrencyConversionRate().getRate())
                        .setScale(clientAccount.getCurrency().getDecimalPlaces(), RoundingMode.HALF_UP));
      }
    }
  }

  @Test
  public void test3FindAccountsByClientIdAndTransactional() throws Exception {
    String jsonResponse = mvc.perform(MockMvcRequestBuilders
            .get("/clients/{clientId}/accounts?transactional=false", 1)
            .contentType(MediaType.APPLICATION_JSON)
    )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    ClientAccount[] clientAccounts = objectMapper.readValue(jsonResponse, ClientAccount[].class);
    Assert.assertEquals(5, clientAccounts.length);

    for (ClientAccount clientAccount : clientAccounts) {
      Assert.assertFalse(clientAccount.getAccountType().getTransactional());

      if ("/".equals(clientAccount.getCurrency().getCurrencyConversionRate().getConversionIndicator())) {
        Assert.assertEquals(clientAccount.getDisplayBalance().setScale(clientAccount.getCurrency().getDecimalPlaces(), RoundingMode.HALF_UP),
                clientAccount.getForexBalance().divide(clientAccount.getCurrency().getCurrencyConversionRate().getRate(),
                        clientAccount.getCurrency().getDecimalPlaces(), RoundingMode.HALF_UP));
      } else {
        Assert.assertEquals(clientAccount.getDisplayBalance().setScale(clientAccount.getCurrency().getDecimalPlaces(), RoundingMode.HALF_UP),
                clientAccount.getForexBalance().multiply(clientAccount.getCurrency().getCurrencyConversionRate().getRate())
                        .setScale(clientAccount.getCurrency().getDecimalPlaces(), RoundingMode.HALF_UP));
      }
    }
  }

  @Test
  public void test4FindAccountsByClientIdAndTransactionalAndAccountTypeCode() throws Exception {
    String jsonResponse = mvc.perform(MockMvcRequestBuilders
            .get("/clients/{clientId}/accounts?transactional=false&accountTypeCode=CFCA", 2)
            .contentType(MediaType.APPLICATION_JSON)
    )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    ClientAccount[] clientAccounts = objectMapper.readValue(jsonResponse, ClientAccount[].class);
    Assert.assertEquals(1, clientAccounts.length);

    for (ClientAccount clientAccount : clientAccounts) {
      Assert.assertFalse(clientAccount.getAccountType().getTransactional());

      if ("/".equals(clientAccount.getCurrency().getCurrencyConversionRate().getConversionIndicator())) {
        Assert.assertEquals(clientAccount.getDisplayBalance().setScale(clientAccount.getCurrency().getDecimalPlaces(), RoundingMode.HALF_UP),
                clientAccount.getForexBalance().divide(clientAccount.getCurrency().getCurrencyConversionRate().getRate(),
                        clientAccount.getCurrency().getDecimalPlaces(), RoundingMode.HALF_UP));
      } else {
        Assert.assertEquals(clientAccount.getDisplayBalance().setScale(clientAccount.getCurrency().getDecimalPlaces(), RoundingMode.HALF_UP),
                clientAccount.getForexBalance().multiply(clientAccount.getCurrency().getCurrencyConversionRate().getRate())
                        .setScale(clientAccount.getCurrency().getDecimalPlaces(), RoundingMode.HALF_UP));
      }
    }
  }

  @Test
  public void test5FindAccountsByClientIdAndAccountTypeCode() throws Exception {
    String jsonResponse = mvc.perform(MockMvcRequestBuilders
            .get("/clients/{clientId}/accounts?accountTypeCode=CFCA", 1)
            .contentType(MediaType.APPLICATION_JSON)
    )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    ClientAccount[] clientAccounts = objectMapper.readValue(jsonResponse, ClientAccount[].class);
    Assert.assertEquals(3, clientAccounts.length);

    for (ClientAccount clientAccount : clientAccounts) {
      Assert.assertFalse(clientAccount.getAccountType().getTransactional());

      if ("/".equals(clientAccount.getCurrency().getCurrencyConversionRate().getConversionIndicator())) {
        Assert.assertEquals(clientAccount.getDisplayBalance().setScale(clientAccount.getCurrency().getDecimalPlaces(), RoundingMode.HALF_UP),
                clientAccount.getForexBalance().divide(clientAccount.getCurrency().getCurrencyConversionRate().getRate(),
                        clientAccount.getCurrency().getDecimalPlaces(), RoundingMode.HALF_UP));
      } else {
        Assert.assertEquals(clientAccount.getDisplayBalance().setScale(clientAccount.getCurrency().getDecimalPlaces(), RoundingMode.HALF_UP),
                clientAccount.getForexBalance().multiply(clientAccount.getCurrency().getCurrencyConversionRate().getRate())
                        .setScale(clientAccount.getCurrency().getDecimalPlaces(), RoundingMode.HALF_UP));
      }
    }
  }

  @Test
  public void test6FindAccountsByClientIdAndAccountTypeCode() throws Exception {
    String jsonResponse = mvc.perform(MockMvcRequestBuilders
            .get("/clients/{clientId}/accounts?transactional=false&accountTypeCode=CHQ", 2)
            .contentType(MediaType.APPLICATION_JSON)
    )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    ClientAccount[] clientAccounts = objectMapper.readValue(jsonResponse, ClientAccount[].class);
    Assert.assertEquals(0, clientAccounts.length);
  }

  @Test
  public void test7FindAccountsByClientIdAndAccountTypeCode() throws Exception {
    String jsonResponse = mvc.perform(MockMvcRequestBuilders
            .get("/clients/{clientId}/accounts?transactional=true&accountTypeCode=CHQ", 2)
            .contentType(MediaType.APPLICATION_JSON)
    )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    ClientAccount[] clientAccounts = objectMapper.readValue(jsonResponse, ClientAccount[].class);
    Assert.assertEquals(1, clientAccounts.length);

    for (ClientAccount clientAccount : clientAccounts) {
      Assert.assertTrue(clientAccount.getAccountType().getTransactional());

      if ("/".equals(clientAccount.getCurrency().getCurrencyConversionRate().getConversionIndicator())) {
        Assert.assertEquals(clientAccount.getDisplayBalance().setScale(clientAccount.getCurrency().getDecimalPlaces(), RoundingMode.HALF_UP),
                clientAccount.getForexBalance().divide(clientAccount.getCurrency().getCurrencyConversionRate().getRate(),
                        clientAccount.getCurrency().getDecimalPlaces(), RoundingMode.HALF_UP));
      } else {
        Assert.assertEquals(clientAccount.getDisplayBalance().setScale(clientAccount.getCurrency().getDecimalPlaces(), RoundingMode.HALF_UP),
                clientAccount.getForexBalance().multiply(clientAccount.getCurrency().getCurrencyConversionRate().getRate())
                        .setScale(clientAccount.getCurrency().getDecimalPlaces(), RoundingMode.HALF_UP));
      }
    }
  }
}