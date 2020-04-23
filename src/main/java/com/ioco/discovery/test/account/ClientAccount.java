package com.ioco.discovery.test.account;

import com.fasterxml.jackson.annotation.JsonView;
import com.ioco.discovery.test.View;
import com.ioco.discovery.test.client.Client;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author hjd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CLIENT_ACCOUNT")
public class ClientAccount implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonView({
    View.List.class,
  })
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Size(min = 1, max = 10)
  @Column(name = "CLIENT_ACCOUNT_NUMBER")
  private String clientAccountNumber;

  @JsonView({
    View.List.class,
  })
  @Column(name = "DISPLAY_BALANCE")
  private BigDecimal displayBalance;
  
  @JsonView({
    View.List.class,
  })
  @JoinColumn(name = "ACCOUNT_TYPE_CODE", referencedColumnName = "ACCOUNT_TYPE_CODE")
  @ManyToOne(optional = false)
  private AccountType accountType;
  
  @JoinColumn(name = "CLIENT_ID", referencedColumnName = "CLIENT_ID")
  @ManyToOne(optional = false)
  private Client client;
  
  @JsonView({
    View.List.class,
  })
  @JoinColumn(name = "CURRENCY_CODE", referencedColumnName = "CURRENCY_CODE")
  @ManyToOne(optional = false)
  private Currency currency;
  
  
  @OneToOne(cascade = CascadeType.ALL, mappedBy = "clientAccount")
  private CreditCardLimit creditCardLimit;

  @JsonView({
    View.List.class,
  })
  @Transient
  private BigDecimal forexBalance;
  
  @JsonView({
    View.List.class,
  })
  @Transient
  private BigDecimal forexDisplayBalance;

  public BigDecimal getForexBalance() {
    if (currency.getCurrencyConversionRate().getConversionIndicator().equals("*")) {
      forexBalance = displayBalance.divide(currency.getCurrencyConversionRate().getRate(), 5, RoundingMode.HALF_UP);
      return forexBalance;
    } else {
      forexBalance = displayBalance.multiply(currency.getCurrencyConversionRate().getRate()).setScale(5, RoundingMode.HALF_UP);
      return forexBalance;
    }
  }
  
  public BigDecimal getForexDisplayBalance() {
      return forexBalance.setScale(currency.getDecimalPlaces(), RoundingMode.HALF_UP);
  }
}
