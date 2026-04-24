package app.domain.services;

import app.domain.exceptions.NotFoundException;
import app.domain.models.Account;
import app.domain.ports.AccountPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAccount {

    private final AccountPort accountPort;

    public FindAccount(AccountPort accountPort) {
        this.accountPort = accountPort;
    }

    public Account findByNumber(int accountNumber) {
        Account account = accountPort.findByNumber(accountNumber);
        if (account == null) throw new NotFoundException("No existe una cuenta con ese número");
        return account;
    }

    public List<Account> findByClientId(Long clientId) {
        return accountPort.findByClientId(clientId);
    }

    public List<Account> findAll() {
        return accountPort.findAll();
    }
}
