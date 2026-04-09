package app.application.adapters.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.application.adapters.persistence.repository.TransferRepository;
import app.domain.models.Transaction;
import app.domain.models.enums.TransactionState;
import app.domain.ports.TransferPort;

@Repository
public class TransferAdapter implements TransferPort {

    @Autowired
    private TransferRepository transferRepository;

    @Override
    public Transaction save(Transaction transaction) {
        return transferRepository.save(transaction);
    }

    @Override
    public Transaction findById(String transferId) {
        return transferRepository.findById(transferId).orElse(null);
    }

    @Override
    public List<Transaction> findByAccount(String accountNumber) {
        return transferRepository.findByOriginAccount(accountNumber);
    }

    @Override
    public List<Transaction> findPendingTransfers() {
        return transferRepository.findByTransactionState(TransactionState.PENDING);
    }
}
