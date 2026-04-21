package app.application.adapters.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.application.adapters.persistence.entities.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {

    AccountEntity findByNumber(int number);
    List<AccountEntity> findByClientId(String clientId);
}