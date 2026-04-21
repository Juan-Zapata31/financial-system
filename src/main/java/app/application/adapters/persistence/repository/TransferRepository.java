package app.application.adapters.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.application.adapters.persistence.entities.TransactionEntity;
import app.domain.models.enums.TransactionState;

public interface TransferRepository extends JpaRepository<TransactionEntity, Integer> {

    List<TransactionEntity> findByOriginAccount(Integer originAccount);
    List<TransactionEntity> findByTransactionState(TransactionState state);
    List<TransactionEntity> findByCreatorUserId(Long userId);
    List<TransactionEntity> findByTransactionStateAndCreatedAtBefore(TransactionState state, LocalDateTime date);
}
