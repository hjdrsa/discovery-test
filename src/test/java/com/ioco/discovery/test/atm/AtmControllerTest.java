package com.ioco.discovery.test.atm;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Map;
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
public class AtmControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void test1Withdraw() throws Exception {
    String inputJson = "{\"atmId\": 1,\"clientId\": 1,\"accountNumber\": \"4067342946\",\"requiredAmount\": 1490}";
    String jsonResponse = mvc.perform(
            MockMvcRequestBuilders
                    .put("/atms/1/withdrawal")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(inputJson)
    )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    WithdrawResponse withdrawResponse = objectMapper.readValue(jsonResponse, WithdrawResponse.class);
    Map<BigDecimal, Integer> noteAllocation = withdrawResponse.getNotes();

    Assert.assertEquals(5, noteAllocation.size());

    noteAllocation.entrySet().forEach((Map.Entry<BigDecimal, Integer> entry) -> {
      BigDecimal denomination = entry.getKey();
      int value = entry.getValue();
      switch (denomination.intValue()) {
        case 200:
          Assert.assertEquals(2L, value);
          break;
        case 100:
          Assert.assertEquals(3L, value);
          break;
        case 50:
          Assert.assertEquals(4L, value);
          break;
        case 20:
          Assert.assertEquals(5L, value);
          break;
        case 10:
          Assert.assertEquals(49L, value);
          break;
        default:
          break;
      }
    });
  }
  
  @Test
  public void test2Withdraw() throws Exception {
    String inputJson = "{\"atmId\": 1,\"clientId\": 1,\"accountNumber\": \"4067342946\",\"requiredAmount\": 20}";
    String jsonResponse = mvc.perform(
            MockMvcRequestBuilders
                    .put("/atms/1/withdrawal")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(inputJson)
    )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    WithdrawResponse withdrawResponse = objectMapper.readValue(jsonResponse, WithdrawResponse.class);
    Map<BigDecimal, Integer> noteAllocation = withdrawResponse.getNotes();

    Assert.assertEquals(0, noteAllocation.size());
    Assert.assertEquals("Amount not available, would you like to draw 10", withdrawResponse.getMessage());

  }
  
  @Test
  public void test3Withdraw() throws Exception {
    String inputJson = "{\"atmId\": 1,\"clientId\": 1,\"accountNumber\": \"4067342946\",\"requiredAmount\": 10}";
    String jsonResponse = mvc.perform(
            MockMvcRequestBuilders
                    .put("/atms/1/withdrawal")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(inputJson)
    )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    WithdrawResponse withdrawResponse = objectMapper.readValue(jsonResponse, WithdrawResponse.class);
    Map<BigDecimal, Integer> noteAllocation = withdrawResponse.getNotes();

    Assert.assertEquals(1, noteAllocation.size());

    noteAllocation.entrySet().forEach((Map.Entry<BigDecimal, Integer> entry) -> {
      BigDecimal denomination = entry.getKey();
      int value = entry.getValue();
      switch (denomination.intValue()) {
        case 10:
          Assert.assertEquals(1L, value);
          break;
        default:
          break;
      }
    });
  }
  
  @Test
  public void test4Withdraw() throws Exception {
    String inputJson = "{\"atmId\": 1,\"clientId\": 1,\"accountNumber\": \"4067342946\",\"requiredAmount\": 10}";
    String jsonResponse = mvc.perform(
            MockMvcRequestBuilders
                    .put("/atms/1/withdrawal")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(inputJson)
    )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    WithdrawResponse withdrawResponse = objectMapper.readValue(jsonResponse, WithdrawResponse.class);
    Map<BigDecimal, Integer> noteAllocation = withdrawResponse.getNotes();

    Assert.assertEquals(0, noteAllocation.size());
    Assert.assertEquals("ATM not registered or unfunded", withdrawResponse.getMessage());

    noteAllocation.entrySet().forEach((Map.Entry<BigDecimal, Integer> entry) -> {
      BigDecimal denomination = entry.getKey();
      int value = entry.getValue();
      switch (denomination.intValue()) {
        case 10:
          Assert.assertEquals(1L, value);
          break;
        default:
          break;
      }
    });
  }

  @Test
  public void test5Withdraw() throws Exception {
    String inputJson = "{\"atmId\": 1,\"clientId\": 1,\"accountNumber\": \"4067342946\",\"requiredAmount\": 25000}";
    String jsonResponse = mvc.perform(
            MockMvcRequestBuilders
                    .put("/atms/1/withdrawal")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(inputJson)
    )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    WithdrawResponse withdrawResponse = objectMapper.readValue(jsonResponse, WithdrawResponse.class);
    Map<BigDecimal, Integer> noteAllocation = withdrawResponse.getNotes();

    Assert.assertEquals(0, noteAllocation.size());
    Assert.assertEquals("Insufficient funds", withdrawResponse.getMessage());

    noteAllocation.entrySet().forEach((Map.Entry<BigDecimal, Integer> entry) -> {
      BigDecimal denomination = entry.getKey();
      int value = entry.getValue();
      switch (denomination.intValue()) {
        case 10:
          Assert.assertEquals(1L, value);
          break;
        default:
          break;
      }
    });
  }
}
