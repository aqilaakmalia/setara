package org.synrgy.setara.contact.model;

import jakarta.persistence.*;
import lombok.*;
import org.synrgy.setara.common.model.Auditable;
import org.synrgy.setara.user.model.User;
import org.synrgy.setara.vendor.model.Bank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_saved_accounts")
public class SavedAccount extends Auditable {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
    name = "owner_id",
    nullable = false,
    referencedColumnName = "id"
  )
  private User owner;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
    name = "bank_id",
    nullable = false,
    referencedColumnName = "id"
  )
  private Bank bank;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String accountNumber;

  @Column(nullable = false)
  private String imagePath;

  @Column(
    name = "is_favorite",
    columnDefinition = "boolean default false"
  )
  private boolean favorite;

}
