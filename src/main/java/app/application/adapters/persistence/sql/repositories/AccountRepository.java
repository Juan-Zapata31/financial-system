package app.application.adapters.persistence.sql.repositories;

import app.application.adapters.persistence.sql.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
    Optional<AccountEntity> findByAccountId(int accountId);
    List<AccountEntity> findByClient_ClientId(Long clientId);
    boolean existsByAccountId(int accountId);
}
