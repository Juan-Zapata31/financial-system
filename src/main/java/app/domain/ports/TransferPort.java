package app.domain.ports;
import app.domain.models.Transaction;
import java.util.List;

public interface TransferPort {

    Transaction save(Transaction transaction);
    Transaction findById(String transferId);
    List<Transaction> findByAccount(String accountNumber);
    List<Transaction> findPendingTransfers();

}