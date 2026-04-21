package app.domain.services;

import app.domain.models.Transaction;
import app.domain.models.enums.OperationType;
import app.domain.models.enums.TransactionState;
import app.domain.models.enums.TransactionType;
import app.domain.ports.TransferPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpireTransfer {

    private final TransferPort transferPort;
    private final TransactionLogService transactionLogService;

    @Value("${app.transfer.approval.timeout.minutes:60}")
    private long timeoutMinutes;

    public ExpireTransfer(TransferPort transferPort,
                          TransactionLogService transactionLogService) {
        this.transferPort = transferPort;
        this.transactionLogService = transactionLogService;
    }

    @Scheduled(fixedDelay = 300000) // runs every 5 minutes
    public void expireTransfers() {
        LocalDateTime threshold = LocalDateTime.now().minusMinutes(timeoutMinutes);
        List<Transaction> pending = transferPort.findPendingOlderThan(threshold);

        for (Transaction transfer : pending) {
            transfer.setTransactionState(TransactionState.EXPIRED);
            transferPort.save(transfer);

            Map<String, Object> detail = new HashMap<>();
            detail.put("transferId", transfer.getTransactionId());
            detail.put("createdAt", transfer.getCreatedAt().toString());
            detail.put("expiredAt", LocalDateTime.now().toString());
            detail.put("reason", "Vencida por falta de aprobación en el tiempo establecido");
            detail.put("creatorUserId", transfer.getCreatorUserId());
            detail.put("newState", TransactionState.EXPIRED.name());
            transactionLogService.log(OperationType.TRANSFER_EXPIRED, TransactionType.TRANSFER,
                    transfer.getCreatorUserId(), "SYSTEM", TransactionState.EXPIRED,
                    String.valueOf(transfer.getTransactionId()),
                    "Transferencia vencida por falta de aprobación", detail);
        }
    }
}
