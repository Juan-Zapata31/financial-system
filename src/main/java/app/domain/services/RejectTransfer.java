package app.domain.services;

import app.domain.exceptions.BusinessException;
import app.domain.exceptions.NotFoundException;
import app.domain.models.Transaction;
import app.domain.models.User;
import app.domain.models.enums.OperationType;
import app.domain.models.enums.Roles;
import app.domain.models.enums.TransactionState;
import app.domain.models.enums.TransactionType;
import app.domain.ports.TransferPort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RejectTransfer {

    private final TransferPort transferPort;
    private final TransactionLogService transactionLogService;

    public RejectTransfer(TransferPort transferPort,
                          TransactionLogService transactionLogService) {
        this.transferPort = transferPort;
        this.transactionLogService = transactionLogService;
    }

    public void rejectTransfer(int transferId, User supervisor) {
        if (supervisor == null || supervisor.getRoles() != Roles.CompanySupervisor) {
            throw new BusinessException("UNAUTHORIZED", "Solo el CompanySupervisor puede rechazar transferencias");
        }

        Transaction transfer = transferPort.findById(transferId);
        if (transfer == null) {
            throw new NotFoundException("La transferencia no existe");
        }
        if (transfer.getTransactionState() != TransactionState.PENDING) {
            throw new BusinessException("INVALID_STATE", "La transferencia no está en estado PENDING");
        }

        transfer.setTransactionState(TransactionState.FAILED);
        transfer.setApproverUserId(supervisor.getUserId());
        transferPort.save(transfer);

        Map<String, Object> detail = new HashMap<>();
        detail.put("transferId", transferId);
        detail.put("rejectedBy", supervisor.getUserId());
        detail.put("newState", TransactionState.FAILED.name());
        transactionLogService.log(OperationType.TRANSFER_REJECTED, TransactionType.TRANSFER,
                supervisor.getUserId(), supervisor.getUsername(), TransactionState.FAILED,
                String.valueOf(transferId), "Transferencia rechazada", detail);
    }
}
