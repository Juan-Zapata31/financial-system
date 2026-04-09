package app.application.adapters.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.domain.models.Account;

public interface AccountRepository extends JpaRepository<Account, String> {

    Account findByNumber(int number);
    List<Account> findByClientId(String clientId);
}