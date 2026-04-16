package app.domain.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.models.Transaction;
import app.domain.models.TransactionLog;
import app.domain.models.enums.TransactionState;
import app.domain.models.enums.TransactionType;
import app.domain.ports.TransactionLogPort;
import app.domain.ports.TransferPort;

@Service
public class ExpireTransfer {
    
    @Autowired
    private TransferPort transferPort;

    @Autowired
    private TransactionLogPort transactionLogPort;

    
    // 🟡 4. VENCIMIENTO
    public void expireTransfers() {

        List<Transaction> pendingTransfers = transferPort.findPendingTransfers();

        for (Transaction transfer : pendingTransfers) {

            if (transfer.getCreatedAt().plusMinutes(60).isBefore(LocalDateTime.now())) {

                transfer.setTransactionState(TransactionState.EXPIRED);
                transferPort.save(transfer);

                transactionLogPort.save(new TransactionLog(
                        0,
                        TransactionType.TRANSFER,
                        "SYSTEM",
                        "Transferencia vencida por falta de aprobación",
                        TransactionState.EXPIRED,
                        null
                ));
            }
        }
    }
}
