package app.application.adapters.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.domain.models.Transaction;
import app.domain.models.enums.TransactionState;

public interface TransferRepository extends JpaRepository<Transaction, String> {

    List<Transaction> findByOriginAccount(String originAccount);
    List<Transaction> findByTransactionState(TransactionState state);
}
