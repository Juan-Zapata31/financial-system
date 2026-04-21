package app.application.adapters.persistence.sql;

import app.application.adapters.persistence.sql.entities.TransactionEntity;
import app.application.adapters.persistence.sql.repositories.TransferRepository;
import app.domain.models.Transaction;
import app.domain.models.enums.TransactionState;
import app.domain.ports.TransferPort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TransferAdapter implements TransferPort {

    private final TransferRepository transferRepository;

    public TransferAdapter(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity saved = transferRepository.save(toEntity(transaction));
        transaction.setTransactionId(saved.getTransactionId());
        return toDomain(saved);
    }

    @Override
    public Transaction findById(int transferId) {
        return transferRepository.findById(transferId).map(this::toDomain).orElse(null);
    }

    @Override
    public List<Transaction> findByAccount(int accountNumber) {
        return transferRepository.findByOriginAccount(accountNumber)
                .stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByCreatorUserId(Long userId) {
        return transferRepository.findByCreatorUserId(userId)
                .stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findPendingTransfers() {
        return transferRepository.findByTransactionState(TransactionState.PENDING)
                .stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findPendingOlderThan(LocalDateTime threshold) {
        return transferRepository
                .findByTransactionStateAndCreatedAtBefore(TransactionState.PENDING, threshold)
                .stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByTransactionState(TransactionState state) {
        return transferRepository.findByTransactionState(state)
                .stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findAll() {
        return transferRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    private TransactionEntity toEntity(Transaction t) {
        TransactionEntity e = new TransactionEntity();
        e.setTransactionId(t.getTransactionId() == 0 ? null : t.getTransactionId());
        e.setOriginAccount(t.getOriginAccount());
        e.setDestinationAccount(t.getDestinationAccount());
        e.setAmount(t.getAmount());
        e.setCurrency(t.getCurrency());
        e.setTransactionState(t.getTransactionState());
        e.setTransactionType(t.getTransactionType());
        e.setDescription(t.getDescription());
        e.setCreatedAt(t.getCreatedAt());
        e.setCreatorUserId(t.getCreatorUserId());
        e.setApproverUserId(t.getApproverUserId());
        return e;
    }

    private Transaction toDomain(TransactionEntity e) {
        Transaction t = new Transaction();
        t.setTransactionId(e.getTransactionId());
        t.setOriginAccount(e.getOriginAccount());
        t.setDestinationAccount(e.getDestinationAccount());
        t.setAmount(e.getAmount());
        t.setCurrency(e.getCurrency());
        t.setTransactionState(e.getTransactionState());
        t.setTransactionType(e.getTransactionType());
        t.setDescription(e.getDescription());
        t.setCreatedAt(e.getCreatedAt());
        t.setCreatorUserId(e.getCreatorUserId());
        t.setApproverUserId(e.getApproverUserId());
        return t;
    }
}
