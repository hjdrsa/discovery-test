package com.ioco.discovery.test.account;

import com.fasterxml.jackson.annotation.JsonView;
import com.ioco.discovery.test.View;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
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
@Table(name = "CURRENCY_CONVERSION_RATE")
@XmlRootElement
public class CurrencyConversionRate implements Serializable {

  private static final long serialVersionUID = 1L;
  
  @JsonView({
    View.List.class,
  })
  @Id
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 3)
  @Column(name = "CURRENCY_CODE")
  private String currencyCode;
  
  @JsonView({
    View.List.class,
  })
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 1)
  @Column(name = "CONVERSION_INDICATOR")
  private String conversionIndicator;
  
  @JsonView({
    View.List.class,
  })
  @Basic(optional = false)
  @NotNull
  @Column(name = "RATE")
  private BigDecimal rate;
  @JoinColumn(name = "CURRENCY_CODE", referencedColumnName = "CURRENCY_CODE", insertable = false, updatable = false)
  @OneToOne(optional = false)
  private Currency currency;
}
