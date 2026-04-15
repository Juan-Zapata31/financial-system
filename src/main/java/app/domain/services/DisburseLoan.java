package app.domain.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.application.adapters.persistence.entities.BankLoanEntity;
import app.domain.exceptions.BusinessException;
import app.domain.models.Account;
import app.domain.models.enums.AccountState;
import app.domain.models.enums.LoanState;
import app.domain.ports.AccountPort;
import app.domain.ports.LoanPort;


@Service
public class DisburseLoan {
    
    private final LoanPort loanPort;
    private final AccountPort accountPort;

    @Autowired
    public DisburseLoan(LoanPort loanPort, AccountPort accountPort) {
        this.loanPort = loanPort;
        this.accountPort = accountPort;
    }

        // 💰 DESEMBOLSO
    public void disburseLoan(String loanId) {

        BankLoanEntity loan = loanPort.findById(loanId);
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
