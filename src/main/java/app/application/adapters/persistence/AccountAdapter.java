package app.application.adapters.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.application.adapters.persistence.repository.AccountRepository;
import app.domain.models.Account;
import app.domain.ports.AccountPort;

@Repository
public class AccountAdapter implements AccountPort {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account findByNumber(int accountNumber) {
        return accountRepository.findByNumber(accountNumber);
    }

    @Override
    public List<Account> findByClientId(String clientId) {
        return accountRepository.findByClientId(clientId);
    }
}