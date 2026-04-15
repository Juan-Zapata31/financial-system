package app.domain.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.exceptions.BusinessException;
import app.domain.models.Account;
import app.domain.models.Transaction;
import app.domain.models.TransactionLog;
import app.domain.models.enums.TransactionState;
import app.domain.models.enums.TransactionType;
import app.domain.ports.AccountPort;
import app.domain.ports.TransactionLogPort;
import app.domain.ports.TransferPort;

@Service
public class CreateTransfer {

    @Autowired
    private TransferPort transferPort;

    @Autowired
    private AccountPort accountPort;

    @Autowired
    private TransactionLogPort transactionLogPort;


    private static final BigDecimal APPROVAL_THRESHOLD = new BigDecimal("1000000");

    // 🟢 1. CREAR TRANSFERENCIA
    public void createTransfer(Transaction transfer) {

        if (transfer.getAmount() == null || transfer.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("INVALID_AMOUNT", "El monto debe ser mayor a cero");
        }

        if (transfer.getAmount().compareTo(APPROVAL_THRESHOLD) > 0) {
            transfer.setTransactionState(TransactionState.PENDING);
        } else {
            executeTransfer(transfer);
            transfer.setTransactionState(TransactionState.COMPLETED);
        }

        transferPort.save(transfer);

        transactionLogPort.save(new TransactionLog(
                0,
                TransactionType.TRANSFER,
                "SYSTEM",
                "Transferencia creada",
                transfer.getTransactionState(),
                null
        ));
    }

        // ⚙️ EJECUCIÓN REAL
    private void executeTransfer(Transaction transfer) {

        Account origin = accountPort.findByNumber(transfer.getOriginAccount());
        Account destination = accountPort.findByNumber(transfer.getDestinationAccount());

        if (origin == null || destination == null) {
            throw new BusinessException("ACCOUNT_NOT_FOUND", "Cuenta no encontrada");
        }

        if (origin.getBalance().compareTo(transfer.getAmount()) < 0) {
            throw new BusinessException("INSUFFICIENT_FUNDS", "Saldo insuficiente");
        }

        origin.setBalance(origin.getBalance().subtract(transfer.getAmount()));
        destination.setBalance(destination.getBalance().add(transfer.getAmount()));

        accountPort.save(origin);
        accountPort.save(destination);
    }
}