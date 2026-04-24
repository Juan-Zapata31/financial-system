package app.application.adapters.persistence.sql.repositories;

import app.application.adapters.persistence.sql.entities.BankLoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<BankLoanEntity, Integer> {
    List<BankLoanEntity> findByClient_ClientId(Long clientId);
}
