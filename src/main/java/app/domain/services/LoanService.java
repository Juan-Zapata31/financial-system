package app.domain.services;

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

    // 🟢 CREAR SOLICITUD
    public BankLoan requestLoan(BankLoan loan) {

        loan.setLoanState(LoanState.PENDING); // "En estudio"
        return loanPort.save(loan);
    }

    // 🔵 APROBAR
    public BankLoan approveLoan(String loanId, User user) {

        if (user == null || user.getRoles() != Roles.InternalAnalyst) {
            throw new BusinessException("NO_PERMISOS_APROBAR", "No tiene permisos para aprobar");
        }

        BankLoan loan = loanPort.findById(loanId);
        if (loan == null) {
            throw new BusinessException("LOAN_NOT_FOUND", "El préstamo no existe");
        }

        if (loan.getLoanState() != LoanState.PENDING) {
            throw new BusinessException("INVALID_STATE", "El préstamo no está en estudio");
        }

        loan.setLoanState(LoanState.APPROVED);

        return loanPort.save(loan);
    }

    // 🔴 RECHAZAR
    public BankLoan rejectLoan(String loanId, User user) {

        if (user == null || user.getRoles() != Roles.InternalAnalyst) {
            throw new BusinessException("NO_PERMISOS_RECHAZAR", "No tiene permisos para rechazar");
        }

        BankLoan loan = loanPort.findById(loanId);
        if (loan == null) {
            throw new BusinessException("LOAN_NOT_FOUND", "El préstamo no existe");
        }

        if (loan.getLoanState() != LoanState.PENDING) {
            throw new BusinessException("INVALID_STATE", "El préstamo no está en estudio");
        }

        loan.setLoanState(LoanState.REJECTED);

        return loanPort.save(loan);
    }

    // 💰 DESEMBOLSO
    public void disburseLoan(String loanId) {

        BankLoan loan = loanPort.findById(loanId);
        if (loan == null) {
            throw new BusinessException("LOAN_NOT_FOUND", "El préstamo no existe");
        }

        if (loan.getLoanState() != LoanState.APPROVED) {
            throw new BusinessException("LOAN_NOT_APPROVED", "El préstamo no está aprobado");
        }

        if (loan.getApprovedAmount() == null ||
            loan.getApprovedAmount().compareTo(BigDecimal.ZERO) <= 0) {

            throw new BusinessException("INVALID_AMOUNT", "El monto aprobado debe ser mayor a cero");
        }

        Account account = accountPort.findByNumber(loan.getDestinationAccount());
        if (account == null) {
            throw new BusinessException("ACCOUNT_NOT_FOUND", "La cuenta destino no existe");
        }

        if (account.getAccountState() != AccountState.ACTIVE) {
            throw new BusinessException("ACCOUNT_NOT_ACTIVE", "La cuenta destino no está activa");
        }

        // 💸 Aumentar saldo
        account.setBalance(account.getBalance().add(loan.getApprovedAmount()));

        // 🔄 Cambiar estado
        loan.setLoanState(LoanState.DISBURDSED);

        // 💾 Guardar
        accountPort.save(account);
        loanPort.save(loan);
    }
}
