package app.domain.services;

import app.domain.exceptions.BusinessException;
import app.domain.exceptions.NotFoundException;
import app.domain.models.Account;
import app.domain.models.Transaction;
import app.domain.models.enums.AccountState;
import app.domain.models.enums.OperationType;
import app.domain.models.enums.Roles;
import app.domain.models.enums.TransactionState;
import app.domain.models.enums.TransactionType;
import app.domain.models.User;
import app.domain.ports.AccountPort;
import app.domain.ports.TransferPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class CreateTransfer {

    private final TransferPort transferPort;
    private final AccountPort accountPort;
    private final TransactionLogService transactionLogService;

    @Value("${app.transfer.approval.threshold:1000000}")
    private BigDecimal approvalThreshold;

    public CreateTransfer(TransferPort transferPort, AccountPort accountPort,
            TransactionLogService transactionLogService) {
        this.transferPort = transferPort;
        this.accountPort = accountPort;
        this.transactionLogService = transactionLogService;
    }

    public Transaction createTransfer(Transaction transfer, User creator) {
        if (transfer.getAmount() == null || transfer.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("INVALID_AMOUNT", "El monto debe ser mayor a cero");
        }

        Account origin = accountPort.findByNumber(transfer.getOriginAccount());
        if (origin == null) {
            throw new NotFoundException("La cuenta de origen no existe");
        }
        if (origin.getAccountState() == AccountState.BLOCKED ||
                origin.getAccountState() == AccountState.CANCELLED) {
            throw new BusinessException("ACCOUNT_NOT_OPERABLE", "La cuenta de origen está bloqueada o cancelada");
        }

        Account destination = accountPort.findByNumber(transfer.getDestinationAccount());
        if (destination == null) {
            throw new NotFoundException("La cuenta de destino no existe");
        }

        transfer.setCreatedAt(LocalDateTime.now());
        transfer.setTransactionType(TransactionType.TRANSFER);
        transfer.setCreatorUserId(creator != null ? creator.getUserId() : null);

        // Determine if approval is needed (CompanyEmployee + above threshold)
        boolean requiresApproval = creator != null
                && creator.getRoles() == Roles.CompanyEmployee
                && transfer.getAmount().compareTo(approvalThreshold) > 0;

        if (requiresApproval) {
            transfer.setTransactionState(TransactionState.PENDING);
            transferPort.save(transfer);

            Map<String, Object> detail = new HashMap<>();
            detail.put("originAccount", transfer.getOriginAccount());
            detail.put("destinationAccount", transfer.getDestinationAccount());
            detail.put("amount", transfer.getAmount());
            detail.put("state", TransactionState.PENDING.name());
            detail.put("reason", "Monto supera umbral de aprobación: " + approvalThreshold);
            transactionLogService.log(OperationType.TRANSFER_CREATED, TransactionType.TRANSFER,
                    creator.getUserId(), creator.getUsername(), TransactionState.PENDING,
                    String.valueOf(transfer.getTransactionId()), "Transferencia pendiente de aprobación", detail);
        } else {
            executeTransfer(transfer, origin, destination,
                    creator != null ? creator.getUserId() : null,
                    creator != null ? creator.getUsername() : "SYSTEM");
        }

        return transfer;
    }

    public void executeTransfer(Transaction transfer, Account origin,
            Account destination, Long userId, String username) {
        if (origin.getBalance().compareTo(transfer.getAmount()) < 0) {
            throw new BusinessException("INSUFFICIENT_FUNDS", "Saldo insuficiente en la cuenta de origen");
        }

        BigDecimal balanceBeforeOrigin = origin.getBalance();
        BigDecimal balanceBeforeDestination = destination.getBalance();

        origin.setBalance(origin.getBalance().subtract(transfer.getAmount()));
        destination.setBalance(destination.getBalance().add(transfer.getAmount()));

        accountPort.save(origin);
        accountPort.save(destination);

        transfer.setTransactionState(TransactionState.COMPLETED);
        transferPort.save(transfer);

        Map<String, Object> detail = new HashMap<>();
        detail.put("originAccount", transfer.getOriginAccount());
        detail.put("destinationAccount", transfer.getDestinationAccount());
        detail.put("amount", transfer.getAmount());
        detail.put("balanceBeforeOrigin", balanceBeforeOrigin);
        detail.put("balanceAfterOrigin", origin.getBalance());
        detail.put("balanceBeforeDestination", balanceBeforeDestination);
        detail.put("balanceAfterDestination", destination.getBalance());
        transactionLogService.log(OperationType.TRANSFER_EXECUTED, TransactionType.TRANSFER,
                userId, username, TransactionState.COMPLETED,
                String.valueOf(transfer.getTransactionId()), "Transferencia ejecutada", detail);
    }
}
