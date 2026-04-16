package app.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.application.adapters.persistence.entities.BankLoanEntity;
import app.domain.models.enums.LoanState;
import app.domain.ports.LoanPort;

@Service
public class RequestLoan {
    
    private final LoanPort loanPort;

    @Autowired
    public RequestLoan(LoanPort loanPort) {
        this.loanPort = loanPort;
    }

    // 🟢 CREAR SOLICITUD
    public BankLoanEntity requestLoan(BankLoanEntity loan) {
        loan.setLoanState(LoanState.PENDING); // "En estudio"
        return loanPort.save(loan);
    }

}
