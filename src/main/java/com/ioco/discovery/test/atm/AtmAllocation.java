package com.ioco.discovery.test.atm;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
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
@Table(name = "ATM_ALLOCATION")
@XmlRootElement
public class AtmAllocation implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "ATM_ALLOCATION_ID")
  private Integer id;
  @Basic(optional = false)
  @NotNull
  @Column(name = "COUNT")
  private int count;
  @JoinColumn(name = "ATM_ID", referencedColumnName = "ATM_ID")
  @ManyToOne(optional = false)
  private Atm atm;
  @JoinColumn(name = "DENOMINATION_ID", referencedColumnName = "DENOMINATION_ID")
  @ManyToOne(optional = false)
  private Denomination denomination;
}
