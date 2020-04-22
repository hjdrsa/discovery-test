package com.ioco.discovery.test.account;

import com.fasterxml.jackson.annotation.JsonView;
import com.ioco.discovery.test.View;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "ACCOUNT_TYPE")
@XmlRootElement
public class AccountType implements Serializable {

  private static final long serialVersionUID = 1L;
  
  @JsonView({
    View.List.class,
  })
  @Id
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 10)
  @Enumerated(EnumType.STRING)
  @Column(name = "ACCOUNT_TYPE_CODE")
  private AccountTypeCode accountTypeCode;
  
  @JsonView({
    View.List.class,
  })
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 50)
  @Column(name = "DESCRIPTION")
  private String description;
  
  @JsonView({
    View.List.class,
  })
  @Column(name = "TRANSACTIONAL")
  private Boolean transactional;
  
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountType")
  private Collection<ClientAccount> clientAccountCollection;
}
