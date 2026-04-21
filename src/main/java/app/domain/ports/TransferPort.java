package app.domain.ports;

import app.domain.models.Transaction;
import app.domain.models.enums.TransactionState;
import java.time.LocalDateTime;
import java.util.List;

public interface TransferPort {
    Transaction save(Transaction transaction);
    Transaction findById(int transferId);
    List<Transaction> findByAccount(int accountNumber);
    List<Transaction> findByCreatorUserId(Long userId);
    List<Transaction> findPendingTransfers();
    List<Transaction> findPendingOlderThan(LocalDateTime threshold);
    List<Transaction> findByTransactionState(TransactionState state);
    List<Transaction> findAll();
}
