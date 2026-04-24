package app.application.adapters.persistence.sql.repositories;

import app.application.adapters.persistence.sql.entities.TransactionEntity;
import app.domain.models.enums.TransactionState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransferRepository extends JpaRepository<TransactionEntity, Integer> {
    List<TransactionEntity> findByOriginAccount(Integer originAccount);
    List<TransactionEntity> findByCreatorUserId(Long creatorUserId);
    List<TransactionEntity> findByTransactionState(TransactionState state);
    List<TransactionEntity> findByTransactionStateAndCreatedAtBefore(
            TransactionState state, LocalDateTime threshold);
}
