package app.application.adapters.persistence.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import app.application.adapters.persistence.entities.BankLoanEntity;


public interface LoanRepository extends JpaRepository<BankLoanEntity, Integer> {

    List<BankLoanEntity> findByClientId(Integer clientId);

}