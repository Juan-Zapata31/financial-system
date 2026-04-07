package app.application.adapters.persistence.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import app.domain.models.BankLoan;

public interface LoanRepository extends JpaRepository<BankLoan, String> {

    List<BankLoan> findByClientId(String clientId);
}