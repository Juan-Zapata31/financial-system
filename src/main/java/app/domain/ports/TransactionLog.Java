package app.domain.ports;

public interface TransactionLogPort {

    public void registerOperation(TransactionLog log);
    public List<TransactionLog> getLogsByUser(String user);
    public List<TransactionLog> getLogsByDate(String date);
}