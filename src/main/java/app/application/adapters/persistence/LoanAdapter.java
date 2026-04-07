package app.application.adapters.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.application.adapters.persistence.repository.LoanRepository;
import app.domain.models.BankLoan;
import app.domain.ports.LoanPort;

@Repository
public class LoanAdapter implements LoanPort {

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public BankLoan save(BankLoan loan) {
        return loanRepository.save(loan);
    }

    @Override
    public BankLoan findById(String loanId) {
        return loanRepository.findById(loanId).orElse(null);
    }

    @Override
    public List<BankLoan> findByClientId(String clientId) {
        return loanRepository.findByClientId(clientId);
    }
}