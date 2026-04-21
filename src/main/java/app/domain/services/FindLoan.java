package app.domain.services;

import app.domain.exceptions.NotFoundException;
import app.domain.models.BankLoan;
import app.domain.ports.LoanPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindLoan {

    private final LoanPort loanPort;

    public FindLoan(LoanPort loanPort) {
        this.loanPort = loanPort;
    }

    public BankLoan findById(int loanId) {
        BankLoan loan = loanPort.findById(loanId);
        if (loan == null) throw new NotFoundException("No existe un préstamo con ese ID");
        return loan;
    }

    public List<BankLoan> findByClientId(Long clientId) {
        return loanPort.findByClientId(clientId);
    }

    public List<BankLoan> findAll() {
        return loanPort.findAll();
    }
}
