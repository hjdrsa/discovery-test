package com.ioco.discovery.test.atm;

import com.ioco.discovery.test.atm.AtmAllocation;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
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
@Table(name = "DENOMINATION")
@XmlRootElement
public class Denomination implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "DENOMINATION_ID")
  private Integer id;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Basic(optional = false)
  @NotNull
  @Column(name = "VALUE")
  private BigDecimal value;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "denomination")
  private Collection<AtmAllocation> atmAllocationCollection;
  @JoinColumn(name = "DENOMINATION_TYPE_CODE", referencedColumnName = "DENOMINATION_TYPE_CODE")
  @ManyToOne
  private DenominationType denominationTypeCode;
}
