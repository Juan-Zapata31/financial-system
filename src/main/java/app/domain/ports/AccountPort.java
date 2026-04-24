package app.domain.ports;

import app.domain.models.Account;
import java.util.List;

public interface AccountPort {
    Account save(Account account);
    Account findByNumber(int accountNumber);
    List<Account> findByClientId(Long clientId);
    List<Account> findAll();
    boolean existsByNumber(int accountNumber);
}
