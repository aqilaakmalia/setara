package org.synrgy.setara.transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.synrgy.setara.transaction.model.Transaction;

import java.util.UUID;

@Repository
public interface TopUpRepository extends JpaRepository<Transaction, UUID> {

  @Modifying
  @Query("UPDATE Transaction t SET t.deletedAt = CURRENT_TIMESTAMP WHERE t.id = :id")
  void deactivateById(@Param("id") UUID id);

  @Modifying
  @Query("UPDATE Transaction t SET t.deletedAt = null WHERE t.id = :id")
  void restoreById(@Param("id") UUID id);

}
