package app.domain.services;

import app.domain.exceptions.BusinessException;
import app.domain.exceptions.NotFoundException;
import app.domain.models.Account;
import app.domain.models.Transaction;
import app.domain.models.User;
import app.domain.models.enums.OperationType;
import app.domain.models.enums.Roles;
import app.domain.models.enums.TransactionState;
import app.domain.models.enums.TransactionType;
import app.domain.ports.AccountPort;
import app.domain.ports.TransferPort;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class ApproveTransfer {

    private final TransferPort transferPort;
    private final AccountPort accountPort;
    private final CreateTransfer createTransfer;
    private final TransactionLogService transactionLogService;

    public ApproveTransfer(TransferPort transferPort, AccountPort accountPort,
            CreateTransfer createTransfer,
            TransactionLogService transactionLogService) {
        this.transferPort = transferPort;
        this.accountPort = accountPort;
        this.createTransfer = createTransfer;
        this.transactionLogService = transactionLogService;
    }

    public void approveTransfer(int transferId, User supervisor) {
        if (supervisor == null || supervisor.getRoles() != Roles.CompanySupervisor) {
            throw new BusinessException("UNAUTHORIZED", "Solo el CompanySupervisor puede aprobar transferencias");
        }

        Transaction transfer = transferPort.findById(transferId);
        if (transfer == null) {
            throw new NotFoundException("La transferencia no existe");
        }
        if (transfer.getTransactionState() != TransactionState.PENDING) {
            throw new BusinessException("INVALID_STATE", "La transferencia no está en estado PENDING");
        }

        Account origin = accountPort.findByNumber(transfer.getOriginAccount());
        Account destination = accountPort.findByNumber(transfer.getDestinationAccount());

        if (origin == null || destination == null) {
            throw new NotFoundException("Cuenta de origen o destino no encontrada");
        }

        transfer.setApproverUserId(supervisor.getUserId());
        createTransfer.executeTransfer(transfer, origin, destination,
                supervisor.getUserId(), supervisor.getUsername());

        Map<String, Object> detail = new HashMap<>();
        detail.put("transferId", transferId);
        detail.put("approvedBy", supervisor.getUserId());
        detail.put("newState", TransactionState.COMPLETED.name());
        transactionLogService.log(OperationType.TRANSFER_EXECUTED, TransactionType.TRANSFER,
                supervisor.getUserId(), supervisor.getUsername(), TransactionState.COMPLETED,
                String.valueOf(transferId), "Transferencia aprobada y ejecutada", detail);
    }
}
