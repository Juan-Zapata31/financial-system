package app.domain.services;

import app.domain.exceptions.BusinessException;
import app.domain.exceptions.NotFoundException;
import app.domain.models.Account;
import app.domain.models.BankLoan;
import app.domain.models.User;
import app.domain.models.enums.AccountState;
import app.domain.models.enums.LoanState;
import app.domain.models.enums.OperationType;
import app.domain.models.enums.Roles;
import app.domain.models.enums.TransactionState;
import app.domain.models.enums.TransactionType;
import app.domain.ports.AccountPort;
import app.domain.ports.LoanPort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class DisburseLoan {

    private final LoanPort loanPort;
    private final AccountPort accountPort;
    private final TransactionLogService transactionLogService;

    public DisburseLoan(LoanPort loanPort, AccountPort accountPort,
                        TransactionLogService transactionLogService) {
        this.loanPort = loanPort;
        this.accountPort = accountPort;
        this.transactionLogService = transactionLogService;
    }

    public void disburseLoan(int loanId, User analyst) {
        if (analyst == null || analyst.getRoles() != Roles.InternalAnalyst) {
            throw new BusinessException("NO_PERMISOS_DESEMBOLSAR", "No tiene permisos para desembolsar préstamos");
        }

        BankLoan loan = loanPort.findById(loanId);
        if (loan == null) {
            throw new NotFoundException("El préstamo no existe");
        }
        if (loan.getLoanState() != LoanState.APPROVED) {
            throw new BusinessException("LOAN_NOT_APPROVED", "El préstamo solo puede desembolsarse si está APPROVED");
        }
        if (loan.getApprovedAmount() == null ||
                loan.getApprovedAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("INVALID_AMOUNT", "El monto aprobado debe ser mayor a cero");
        }

        Account account = accountPort.findByNumber(loan.getDestinationAccount());
        if (account == null) {
            throw new NotFoundException("La cuenta destino del desembolso no existe");
        }
        if (account.getAccountState() != AccountState.ACTIVE) {
            throw new BusinessException("ACCOUNT_NOT_ACTIVE", "La cuenta destino no está activa");
        }
        if (loan.getClient() != null && account.getClient() != null &&
                !account.getClient().getClientId().equals(loan.getClient().getClientId())) {
            throw new BusinessException("ACCOUNT_NOT_BELONGS", "La cuenta destino no pertenece al cliente solicitante");
        }

        BigDecimal balanceBefore = account.getBalance();
        account.setBalance(account.getBalance().add(loan.getApprovedAmount()));
        accountPort.save(account);

        loan.setLoanState(LoanState.DISBURSED);
        loan.setDisbursementDate(LocalDate.now());
        loan.setAnalystId(analyst.getUserId());
        loanPort.save(loan);

        Map<String, Object> detail = new HashMap<>();
        detail.put("loanId", loanId);
        detail.put("destinationAccount", loan.getDestinationAccount());
        detail.put("approvedAmount", loan.getApprovedAmount());
        detail.put("balanceBefore", balanceBefore);
        detail.put("balanceAfter", account.getBalance());
        detail.put("previousState", LoanState.APPROVED.name());
        detail.put("newState", LoanState.DISBURSED.name());
        detail.put("analystId", analyst.getUserId());
        transactionLogService.log(OperationType.LOAN_DISBURSED, TransactionType.LOAN_DISBURSEMENT,
                analyst.getUserId(), analyst.getUsername(), TransactionState.COMPLETED,
                String.valueOf(loanId), "Préstamo desembolsado", detail);
    }
}
