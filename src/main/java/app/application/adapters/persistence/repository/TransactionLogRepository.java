package app.application.adapters.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.application.adapters.persistence.entities.TransactionLogEntity;

public interface TransactionLogRepository extends JpaRepository<TransactionLogEntity, Integer> {

    List<TransactionLogEntity> findByResponsableUser(String user);
    List<TransactionLogEntity> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    List<TransactionLogEntity> findByAffectedProductId(String productId);
}