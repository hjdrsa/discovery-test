package com.ioco.discovery.test.account;

import com.ioco.discovery.test.account.ClientAccount;
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
@Table(name = "CREDIT_CARD_LIMIT")
@XmlRootElement
public class CreditCardLimit implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 10)
  @Column(name = "CLIENT_ACCOUNT_NUMBER")
  private String clientAccountNumber;
  @Basic(optional = false)
  @NotNull
  @Column(name = "ACCOUNT_LIMIT")
  private BigDecimal accountLimit;
  @JoinColumn(name = "CLIENT_ACCOUNT_NUMBER", referencedColumnName = "CLIENT_ACCOUNT_NUMBER", insertable = false, updatable = false)
  @OneToOne(optional = false)
  private ClientAccount clientAccount;

}
