package app.application.adapters.persistence.mongodb;

import app.application.adapters.persistence.mongodb.documents.TransactionLogDocument;
import app.application.adapters.persistence.mongodb.repositories.TransactionLogMongoRepository;
import app.domain.models.TransactionLog;
import app.domain.ports.TransactionLogPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TransactionLogAdapter implements TransactionLogPort {

    private final TransactionLogMongoRepository repository;

    public TransactionLogAdapter(TransactionLogMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(TransactionLog log) {
        repository.save(toDocument(log));
    }

    @Override
    public List<TransactionLog> findByAffectedProductId(String productId) {
        return repository.findByAffectedProductId(productId)
                .stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<TransactionLog> findByResponsableUser(String username) {
        return repository.findByResponsableUser(username)
                .stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<TransactionLog> findAll() {
        return repository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    private TransactionLogDocument toDocument(TransactionLog l) {
        TransactionLogDocument d = new TransactionLogDocument();
        d.setRegisterId(l.getRegisterId());
        d.setOperationType(l.getOperationType());
        d.setTransactionType(l.getTransactionType());
        d.setOperationDateTime(l.getOperationDateTime());
        d.setResponsableUserId(l.getResponsableUserId());
        d.setResponsableUser(l.getResponsableUser());
        d.setDescription(l.getDescription());
        d.setTransactionState(l.getTransactionState());
        d.setAffectedProductId(l.getAffectedProductId());
        d.setDetail(l.getDetail());
        return d;
    }

    private TransactionLog toDomain(TransactionLogDocument d) {
        TransactionLog l = new TransactionLog();
        l.setRegisterId(d.getRegisterId());
        l.setOperationType(d.getOperationType());
        l.setTransactionType(d.getTransactionType());
        l.setOperationDateTime(d.getOperationDateTime());
        l.setResponsableUserId(d.getResponsableUserId());
        l.setResponsableUser(d.getResponsableUser());
        l.setDescription(d.getDescription());
        l.setTransactionState(d.getTransactionState());
        l.setAffectedProductId(d.getAffectedProductId());
        l.setDetail(d.getDetail());
        return l;
    }
}
