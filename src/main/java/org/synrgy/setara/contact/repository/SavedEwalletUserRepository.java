package org.synrgy.setara.contact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.synrgy.setara.contact.model.SavedEwalletUser;
import org.synrgy.setara.user.model.EwalletUser;
import org.synrgy.setara.user.model.User;

import java.util.UUID;

@Repository
public interface SavedEwalletUserRepository extends JpaRepository<SavedEwalletUser, UUID> {

  @Modifying
  @Query("UPDATE SavedEwalletUser su SET su.deletedAt = CURRENT_TIMESTAMP WHERE su.id = :id")
  void deactivateById(UUID id);

  @Modifying
  @Query("UPDATE SavedEwalletUser su SET su.deletedAt = null WHERE su.id = :id")
  void restoreById(@Param("id") UUID id);

  boolean existsByOwnerAndEwalletUser(User owner, EwalletUser ewalletUser);
}
