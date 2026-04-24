package app.domain.services;

import app.domain.models.TransactionLog;
import app.domain.models.enums.OperationType;
import app.domain.models.enums.TransactionState;
import app.domain.models.enums.TransactionType;
import app.domain.ports.TransactionLogPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class TransactionLogService {

    private final TransactionLogPort transactionLogPort;

    public TransactionLogService(TransactionLogPort transactionLogPort) {
        this.transactionLogPort = transactionLogPort;
    }

    public void log(OperationType operationType, TransactionType transactionType,
                    Long userId, String username, TransactionState state,
                    String affectedProductId, String description,
                    Map<String, Object> detail) {
        TransactionLog log = new TransactionLog();
        log.setOperationType(operationType);
        log.setTransactionType(transactionType);
        log.setOperationDateTime(LocalDateTime.now());
        log.setResponsableUserId(userId);
        log.setResponsableUser(username != null ? username : "SYSTEM");
        log.setDescription(description);
        log.setTransactionState(state);
        log.setAffectedProductId(affectedProductId);
        log.setDetail(detail);
        transactionLogPort.save(log);
    }

    public List<TransactionLog> findByProduct(String productId) {
        return transactionLogPort.findByAffectedProductId(productId);
    }

    public List<TransactionLog> findAll() {
        return transactionLogPort.findAll();
    }
}
