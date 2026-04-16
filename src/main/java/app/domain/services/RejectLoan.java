package app.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.application.adapters.persistence.entities.BankLoanEntity;
import app.domain.exceptions.BusinessException;
import app.domain.models.User;
import app.domain.models.enums.LoanState;
import app.domain.models.enums.Roles;
import app.domain.ports.LoanPort;

@Service
public class RejectLoan {

    private final LoanPort loanPort;

    @Autowired
    public RejectLoan(LoanPort loanPort) {
        this.loanPort = loanPort;
    }

    // 🔴 RECHAZAR
    public BankLoanEntity rejectLoan(String loanId, User user) {

        if (user == null || user.getRoles() != Roles.InternalAnalyst) {
            throw new BusinessException("NO_PERMISOS_RECHAZAR", "No tiene permisos para rechazar");
        }

        BankLoanEntity loan = loanPort.findById(loanId);
        if (loan == null) {
            throw new BusinessException("LOAN_NOT_FOUND", "El préstamo no existe");
        }

        if (loan.getLoanState() != LoanState.PENDING) {
            throw new BusinessException("INVALID_STATE", "El préstamo no está en estudio");
        }

        loan.setLoanState(LoanState.REJECTED);

        return loanPort.save(loan);
    }
}
