package app.domain.ports;

import app.domain.models.TransactionLog;
import java.util.List;

public interface TransactionLogPort {
    void save(TransactionLog log);
    List<TransactionLog> findByAffectedProductId(String productId);
    List<TransactionLog> findByResponsableUser(String username);
    List<TransactionLog> findAll();
}
