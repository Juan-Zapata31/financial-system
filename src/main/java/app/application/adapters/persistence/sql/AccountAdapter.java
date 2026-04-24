package app.application.adapters.persistence.sql;

import app.application.adapters.persistence.sql.entities.AccountEntity;
import app.application.adapters.persistence.sql.repositories.AccountRepository;
import app.application.adapters.persistence.sql.repositories.ClientRepository;
import app.domain.models.Account;
import app.domain.ports.AccountPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AccountAdapter implements AccountPort {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;
    private final ClientAdapter clientAdapter;

    public AccountAdapter(AccountRepository accountRepository,
                          ClientRepository clientRepository,
                          ClientAdapter clientAdapter) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
        this.clientAdapter = clientAdapter;
    }

    @Override
    public Account save(Account account) {
        AccountEntity saved = accountRepository.save(toEntity(account));
        return toDomain(saved);
    }

    @Override
    public Account findByNumber(int accountNumber) {
        return accountRepository.findByAccountId(accountNumber).map(this::toDomain).orElse(null);
    }

    @Override
    public List<Account> findByClientId(Long clientId) {
        return accountRepository.findByClient_ClientId(clientId)
                .stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public boolean existsByNumber(int accountNumber) {
        return accountRepository.existsByAccountId(accountNumber);
    }

    private AccountEntity toEntity(Account a) {
        AccountEntity e = new AccountEntity();
        e.setAccountId(a.getAccountId() == 0 ? null : a.getAccountId());
        e.setAccountType(a.getAccountType());
        e.setBalance(a.getBalance());
        e.setCurrency(a.getCurrency());
        e.setAccountState(a.getAccountState());
        e.setCreationDate(a.getCreationDate());
        if (a.getClient() != null && a.getClient().getClientId() != null) {
            clientRepository.findById(a.getClient().getClientId()).ifPresent(e::setClient);
        }
        return e;
    }

    private Account toDomain(AccountEntity e) {
        Account a = new Account();
        a.setAccountId(e.getAccountId());
        a.setAccountType(e.getAccountType());
        a.setBalance(e.getBalance());
        a.setCurrency(e.getCurrency());
        a.setAccountState(e.getAccountState());
        a.setCreationDate(e.getCreationDate());
        if (e.getClient() != null) {
            a.setClient(clientAdapter.toDomain(e.getClient()));
        }
        return a;
    }
}
