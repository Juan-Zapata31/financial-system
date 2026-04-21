package app.domain.services;

import app.domain.exceptions.BusinessException;
import app.domain.exceptions.NotFoundException;
import app.domain.models.BankLoan;
import app.domain.models.User;
import app.domain.models.enums.LoanState;
import app.domain.models.enums.OperationType;
import app.domain.models.enums.Roles;
import app.domain.models.enums.TransactionState;
import app.domain.models.enums.TransactionType;
import app.domain.ports.LoanPort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class ApproveLoan {

    private final LoanPort loanPort;
    private final TransactionLogService transactionLogService;

    public ApproveLoan(LoanPort loanPort, TransactionLogService transactionLogService) {
        this.loanPort = loanPort;
        this.transactionLogService = transactionLogService;
    }

    public BankLoan approveLoan(int loanId, BigDecimal approvedAmount,
                                BigDecimal interestRate, User analyst) {
        if (analyst == null || analyst.getRoles() != Roles.InternalAnalyst) {
            throw new BusinessException("NO_PERMISOS_APROBAR", "No tiene permisos para aprobar préstamos");
        }

        BankLoan loan = loanPort.findById(loanId);
        if (loan == null) {
            throw new NotFoundException("El préstamo no existe");
        }
        if (loan.getLoanState() != LoanState.PENDING) {
            throw new BusinessException("INVALID_STATE", "El préstamo solo puede aprobarse si está en estado PENDING");
        }
        if (approvedAmount == null || approvedAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("INVALID_AMOUNT", "El monto aprobado debe ser mayor a cero");
        }

        loan.setApprovedAmount(approvedAmount);
        loan.setInterestRate(interestRate);
        loan.setLoanState(LoanState.APPROVED);
        loan.setApprovedDate(LocalDate.now());
        loan.setAnalystId(analyst.getUserId());

        BankLoan saved = loanPort.save(loan);

        Map<String, Object> detail = new HashMap<>();
        detail.put("approvedAmount", approvedAmount);
        detail.put("interestRate", interestRate);
        detail.put("previousState", LoanState.PENDING.name());
        detail.put("newState", LoanState.APPROVED.name());
        detail.put("analystId", analyst.getUserId());
        transactionLogService.log(OperationType.LOAN_APPROVED, TransactionType.LOAN_REPAYMENT,
                analyst.getUserId(), analyst.getUsername(), TransactionState.AUTHORIZED,
                String.valueOf(loanId), "Préstamo aprobado", detail);

        return saved;
    }
}
