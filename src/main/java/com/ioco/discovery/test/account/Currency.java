package com.ioco.discovery.test.account;

import com.ioco.discovery.test.account.ClientAccount;
import com.fasterxml.jackson.annotation.JsonView;
import com.ioco.discovery.test.View;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "CURRENCY")
@XmlRootElement
public class Currency implements Serializable {

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
  @Column(name = "DECIMAL_PLACES")
  private int decimalPlaces;
  
  @JsonView({
    View.List.class,
  })
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 255)
  @Column(name = "DESCRIPTION")
  private String description;
  
  @JsonView({
    View.List.class,
  })
  @OneToOne(cascade = CascadeType.ALL, mappedBy = "currency")
  private CurrencyConversionRate currencyConversionRate;
  
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "currency")
  private Collection<ClientAccount> clientAccountCollection;

}
