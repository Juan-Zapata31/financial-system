package app.domain.ports;

import java.time.LocalDateTime;
import java.util.List;
import app.domain.models.TransactionLog;

public interface TransactionLogPort {

    TransactionLogPort save(TransactionLog log);
    List<TransactionLog> findByUser(String user);
    List<TransactionLog> findByDate(LocalDateTime start, LocalDateTime end);
}
