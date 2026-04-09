package app.application.adapters.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.application.adapters.persistence.entities.BankLoanEntity;
import app.application.adapters.persistence.repository.LoanRepository;
import app.domain.ports.LoanPort;

@Repository
public class LoanAdapter implements LoanPort {

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public BankLoanEntity save(BankLoanEntity loan) {
        return loanRepository.save(loan);
    }

    @Override
    public BankLoanEntity findById(String loanId) {
        return loanRepository.findById(Integer.parseInt(loanId)).orElse(null);
    }

    @Override
    public List<BankLoanEntity> findByClientId(Integer clientId) {
        return loanRepository.findByClientId(clientId);
    }
}