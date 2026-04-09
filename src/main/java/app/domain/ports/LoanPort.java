package app.domain.ports;

import java.util.List;

import app.application.adapters.persistence.entities.BankLoanEntity;

public interface LoanPort {

    BankLoanEntity save(BankLoanEntity loan);
    BankLoanEntity findById(String loanId);
    List<BankLoanEntity> findByClientId(Integer clientId);
}