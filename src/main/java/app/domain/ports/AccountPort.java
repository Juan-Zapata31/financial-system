package app.domain.ports;

import java.util.List;

import app.domain.models.Account;

public interface AccountPort {

    Account save(Account account);
    Account findByNumber(int destinationAccount);
    List<Account> findByClientId(String clientId);
}
