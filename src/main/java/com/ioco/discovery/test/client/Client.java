package com.ioco.discovery.test.client;

import com.ioco.discovery.test.account.ClientAccount;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
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
@Table(name = "CLIENT")
public class Client implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "CLIENT_ID")
  private Integer id;
  @Size(max = 10)
  @Column(name = "TITLE")
  private String title;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 255)
  @Column(name = "NAME")
  private String name;
  @Size(max = 100)
  @Column(name = "SURNAME")
  private String surname;
  @Basic(optional = false)
  @NotNull
  @Column(name = "DOB")
  @Temporal(TemporalType.DATE)
  private Date dob;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
  private Collection<ClientAccount> clientAccountCollection;
  @JoinColumn(name = "CLIENT_SUB_TYPE_CODE", referencedColumnName = "CLIENT_SUB_TYPE_CODE")
  @ManyToOne(optional = false)
  private ClientSubType clientSubTypeCode;
}
