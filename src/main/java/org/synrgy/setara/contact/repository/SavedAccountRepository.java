package org.synrgy.setara.contact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.synrgy.setara.contact.model.SavedAccount;

import java.util.UUID;

@Repository
public interface SavedAccountRepository extends JpaRepository<SavedAccount, UUID> {

  @Modifying
  @Query("UPDATE SavedAccount sa SET sa.deletedAt = CURRENT_TIMESTAMP WHERE sa.id = :id")
  void deactivateById(UUID id);

  @Modifying
  @Query("UPDATE SavedAccount sa SET sa.deletedAt = null WHERE sa.id = :id")
  void restoreById(@Param("id") UUID id);

}
