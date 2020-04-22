package com.ioco.discovery.test.client;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "CLIENT_SUB_TYPE")
@XmlRootElement
public class ClientSubType implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 4)
  @Column(name = "CLIENT_SUB_TYPE_CODE")
  private String clientSubTypeCode;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 255)
  @Column(name = "DESCRIPTION")
  private String description;
  @JoinColumn(name = "CLIENT_TYPE_CODE", referencedColumnName = "CLIENT_TYPE_CODE")
  @ManyToOne(optional = false)
  private ClientType clientTypeCode;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientSubTypeCode")
  private Collection<Client> clientCollection;  
}
