package org.synrgy.setara.transaction.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.synrgy.setara.common.model.Auditable;
import org.synrgy.setara.user.model.User;
import org.synrgy.setara.vendor.model.Bank;
import org.synrgy.setara.vendor.model.Ewallet;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_transactions")
public class Transaction extends Auditable {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
    name = "user_id",
    nullable = false,
    referencedColumnName = "id"
  )
  private User user;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(
    name = "bank_id",
    referencedColumnName = "id"
  )
  private Bank bank;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
    name = "ewallet_id",
    referencedColumnName = "id"
  )
  private Ewallet ewallet;

  @Enumerated(EnumType.STRING)
  private TransactionType type;

  private String destinationAccountNumber;

  private String destinationPhoneNumber;

  private BigDecimal amount;

  private BigDecimal adminFee;

  private BigDecimal totalamount;

  @Column(unique = true)
  private String uniqueCode;

  @Column(
    name = "reference_no",
    unique = true
  )
  private String referenceNumber;

  private String note;

  private LocalDateTime time;

}
