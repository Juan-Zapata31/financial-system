package app.domain.services;

import app.domain.exceptions.NotFoundException;
import app.domain.models.Transaction;
import app.domain.models.enums.TransactionState;
import app.domain.ports.TransferPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindTransfer {

    private final TransferPort transferPort;

    public FindTransfer(TransferPort transferPort) {
        this.transferPort = transferPort;
    }

    public Transaction findById(int transferId) {
        Transaction transfer = transferPort.findById(transferId);
        if (transfer == null) throw new NotFoundException("No existe una transferencia con ese ID");
        return transfer;
    }

    public List<Transaction> findByAccount(int accountNumber) {
        return transferPort.findByAccount(accountNumber);
    }

    public List<Transaction> findByCreator(Long userId) {
        return transferPort.findByCreatorUserId(userId);
    }

    public List<Transaction> findByState(TransactionState state) {
        return transferPort.findByTransactionState(state);
    }

    public List<Transaction> findAll() {
        return transferPort.findAll();
    }
}
