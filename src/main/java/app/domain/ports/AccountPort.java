package app.domain.ports;

import java.util.List;

public interface AccountPort {

    public AccountPort createAccount(AccountPort account);
    public AccountPort getAccountByNumber(String accountNumber);
    public List<AccountPort> getAccountsByClient(String clientId);
    public void updateBalance(String accountNumber, double newBalance);
    public void changeStatus(String accountNumber, String status);

}
