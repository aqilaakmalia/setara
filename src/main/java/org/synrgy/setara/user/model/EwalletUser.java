package org.synrgy.setara.user.model;

import jakarta.persistence.*;
import lombok.*;
import org.synrgy.setara.common.model.Auditable;
import org.synrgy.setara.vendor.model.Ewallet;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_ewallet_users")
public class EwalletUser extends Auditable {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
    name = "ewallet_id",
    nullable = false,
    referencedColumnName = "id"
  )
  private Ewallet ewallet;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String phoneNumber;

  private BigDecimal balance;

}
