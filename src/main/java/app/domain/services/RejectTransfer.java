package app.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.models.User;

import app.domain.exceptions.BusinessException;
import app.domain.models.Transaction;
import app.domain.models.TransactionLog;
import app.domain.models.enums.Roles;
import app.domain.models.enums.TransactionState;
import app.domain.models.enums.TransactionType;
import app.domain.ports.TransactionLogPort;
import app.domain.ports.TransferPort;
import app.domain.ports.UserPort;


@Service
public class RejectTransfer {
    
    
    @Autowired
    private TransferPort transferPort;

    @Autowired
    private TransactionLogPort transactionLogPort;

    @Autowired
    private UserPort userPort;

    // 🔴 3. RECHAZAR TRANSFERENCIA
    public void rejectTransfer(String transferId, String responsableUser) {

        Transaction transfer = transferPort.findById(transferId);
        if (transfer == null) {
            throw new BusinessException("TRANSFER_NOT_FOUND", "La transferencia no existe");
        }

        User approver = userPort.findByUsername(responsableUser);
        if (approver == null) {
            throw new BusinessException("USER_NOT_FOUND", "El usuario no existe");
        }

        if (approver.getRoles() != Roles.CompanySupervisor) {
            throw new BusinessException("UNAUTHORIZED", "Sin permisos");
        }

        if (transfer.getTransactionState() != TransactionState.PENDING) {
            throw new BusinessException("INVALID_STATE", "No está pendiente");
        }

        transfer.setTransactionState(TransactionState.FAILED);
        transferPort.save(transfer);

        transactionLogPort.save(new TransactionLog(
                0,
                TransactionType.TRANSFER,
                responsableUser,
                "Transferencia rechazada",
                TransactionState.FAILED,
                null
        ));
    }
}
