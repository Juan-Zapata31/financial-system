package app.domain.application.services;

import app.domain.models.BankLoan;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.exceptions.BusinessException;
import app.domain.models.Account;
import app.domain.models.User;
import app.domain.models.enums.LoanState;
import app.domain.models.enums.AccountState;
import app.domain.models.enums.Roles;
import app.domain.ports.LoanPort;
import app.domain.ports.AccountPort;

@Service
public class LoanService {

    private final LoanPort loanPort;
    private final AccountPort accountPort;

    @Autowired
    public LoanService(LoanPort loanPort, AccountPort accountPort) {
        this.loanPort = loanPort;
        this.accountPort = accountPort;
    }

    public BankLoan requestLoan(BankLoan loan) {
        loan.setLoanState(LoanState.PENDING);
        return loanPort.save(loan);
    }

    //SIN PERMISOS PARA RECHAZAR O APROBAR, SOLO ANALISTAS INTERNOS
    public BankLoan approveLoan(String loanId, User user) {

        if (user.getRoles() != Roles.InternalAnalyst) {
            throw new BusinessException("NO_PERMISOS_APROBAR", "No tiene permisos para aprobar");
        }

        BankLoan loan = loanPort.findById(loanId);
        loan.setLoanState(LoanState.APPROVED);

        return loanPort.save(loan);
    }

    //SIN PERMISOS PARA RECHAZAR O APROBAR, SOLO ANALISTAS INTERNOS
    public BankLoan rejectLoan(String loanId, User user) {

        if (user.getRoles() != Roles.InternalAnalyst) {
            throw new BusinessException("NO_PERMISOS_RECHAZAR", "No tiene permisos para rechazar");
        }

        BankLoan loan = loanPort.findById(loanId);
        loan.setLoanState(LoanState.REJECTED);

        return loanPort.save(loan);
    }

    //DESEMBOLSO DE DINERO PARA EL PRÉSTAMO APROBADO
    public void disburseLoan(String loanId) {
        BankLoan loan = loanPort.findById(loanId);

        if (loan.getLoanState() != LoanState.APPROVED) {
            throw new BusinessException("PRÉSTAMO_NO_APROBADO", "El préstamo no está aprobado");
        }

        Account account = accountPort.findByNumber(loan.getDestinationAccount());

        if (account.getAccountState() != AccountState.ACTIVE) {
            throw new BusinessException("CUENTA_NO_ACTIVA", "La cuenta destino no está activa");
        }

        BigDecimal newBalance = account.getBalance().add(loan.getApprovedAmount());
        account.setBalance(newBalance);
        loan.setLoanState(LoanState.DISBURDSED);
        accountPort.save(account);
        loanPort.save(loan);
    }
}
