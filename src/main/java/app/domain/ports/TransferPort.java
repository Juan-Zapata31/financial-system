package app.domain.ports;

import java.util.List;

public interface TransferPort {

    public TransferPort makeTransfer(TransferPort transfer);
    public TransferPort getTransferById(String transferId);
    public List<TransferPort> getTransfersByAccount(String accountNumber);

}