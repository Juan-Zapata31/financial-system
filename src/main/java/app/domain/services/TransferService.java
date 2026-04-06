package app.domain.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.exceptions.BusinessException;
import app.domain.models.Account;
import app.domain.models.Transaction;
import app.domain.models.TransactionLog;
import app.domain.models.enums.Roles;
import app.domain.models.enums.TransactionState;
import app.domain.models.enums.TransactionType;
import app.domain.ports.AccountPort;
import app.domain.ports.TransferPort;
import app.domain.ports.TransactionLogPort;
import app.domain.ports.UserPort;
import app.domain.models.User;
import java.util.List;

@Service
public class TransferService {

    @Autowired
    private TransferPort transferPort;
    @Autowired
    private AccountPort accountPort;
    @Autowired
    private TransactionLogPort transactionLogPort;
    @Autowired
    private UserPort userPort;

    public TransferService(TransferPort transferPort, AccountPort accountPort, TransactionLogPort transactionLogPort) {
        this.transferPort = transferPort;
        this.accountPort = accountPort;
        this.transactionLogPort = transactionLogPort;
    }

    private static final BigDecimal APPROVAL_THRESHOLD = new BigDecimal(1000000);

    // 🟢 1. CREAR TRANSFERENCIA
    public void createTransfer(Transaction transfer) {

        if (transfer.getAmount().compareTo(APPROVAL_THRESHOLD) > 0) {
            transfer.setTransactionState(TransactionState.PENDING);
        } else {
            executeTransfer(transfer);
            transfer.setTransactionState(TransactionState.AUTHORIZED);
        }

        transferPort.save(transfer);
    }

    // 🔵 2. APROBAR TRANSFERENCIA
    public void approveTransfer(String transferId, String responsableUser) {

        Transaction transfer = transferPort.findById(transferId);
        User approver = userPort.findByUsername(responsableUser);

        if (approver == null) {
            throw new BusinessException("USER_NOT_FOUND", "El usuario no existe");
        }

        if (approver.getRoles() != Roles.CompanySupervisor) {
            throw new BusinessException("UNAUTHORIZED", "El usuario no tiene permisos para aprobar transferencias");
        }

        if (transfer == null) {
            throw new BusinessException("TRANSFER_NOT_FOUND", "La transferencia no existe");
        }

        if (transfer.getTransactionState() != TransactionState.PENDING) {
            throw new BusinessException("INVALID_STATE", "La transferencia no está pendiente");
        }

        Account origin = accountPort.findByNumber(transfer.getOriginAccount());
        Account destination = accountPort.findByNumber(transfer.getDestinationAccount());

        if (origin.getBalance().compareTo(transfer.getAmount()) < 0) {
            throw new BusinessException("INSUFFICIENT_FUNDS", "Saldo insuficiente");
        }

        // Ejecutar transferencia
        origin.setBalance(origin.getBalance().subtract(transfer.getAmount()));
        destination.setBalance(destination.getBalance().add(transfer.getAmount()));

        transfer.setTransactionState(TransactionState.COMPLETED);

        accountPort.save(origin);
        accountPort.save(destination);
        transferPort.save(transfer);

        transactionLogPort.save(new TransactionLog(
                0,
                TransactionType.TRANSFER,
                responsableUser,
                "Transferencia aprobada y ejecutada",
                TransactionState.COMPLETED,
                origin));
    }

    // 🔴 3. RECHAZAR TRANSFERENCIA
    public void rejectTransfer(String transferId, String responsableUser) {

        Transaction transfer = transferPort.findById(transferId);
        User approver = userPort.findByUsername(responsableUser);

        if (approver == null) {
            throw new BusinessException("USER_NOT_FOUND", "El usuario no existe");
        }

        if (approver.getRoles() != Roles.CompanySupervisor) {
            throw new BusinessException("UNAUTHORIZED", "El usuario no tiene permisos para aprobar transferencias");
        }

        if (transfer == null) {
            throw new BusinessException("TRANSFER_NOT_FOUND", "La transferencia no existe");
        }

        if (transfer.getTransactionState() != TransactionState.PENDING) {
            throw new BusinessException("INVALID_STATE", "La transferencia no está pendiente");
        }

        transfer.setTransactionState(TransactionState.FAILED);

        transferPort.save(transfer);

        transactionLogPort.save(new TransactionLog(
                0,
                TransactionType.TRANSFER,
                responsableUser,
                "Transferencia rechazada",
                TransactionState.FAILED,
                null));
    }

    // 🟡 4. VENCIMIENTO AUTOMÁTICO
    public void expireTransfers() {

        List<Transaction> pendingTransfers = transferPort.findPendingTransfers();

        for (Transaction transfer : pendingTransfers) {

            if (transfer.getCreatedAt().plusMinutes(60).isBefore(LocalDateTime.now())) {

                transfer.setTransactionState(TransactionState.EXPIRED);

                transferPort.save(transfer);

                transactionLogPort.save(new TransactionLog(
                        0,
                        TransactionType.TRANSFER,
                        "Sistema",
                        "Transferencia vencida por falta de aprobación en el tiempo establecido",
                        TransactionState.EXPIRED,
                        null));
            }
        }
    }

    // ⚙️ MÉTODO INTERNO
    private void executeTransfer(Transaction transfer) {

        Account origin = accountPort.findByNumber(transfer.getOriginAccount());
        Account destination = accountPort.findByNumber(transfer.getDestinationAccount());

        if (origin.getBalance().compareTo(transfer.getAmount()) < 0) {
            throw new BusinessException("INSUFFICIENT_FUNDS", "Saldo insuficiente");
        }

        origin.setBalance(origin.getBalance().subtract(transfer.getAmount()));
        destination.setBalance(destination.getBalance().add(transfer.getAmount()));

        accountPort.save(origin);
        accountPort.save(destination);
    }
}
