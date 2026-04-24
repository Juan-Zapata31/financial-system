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

import java.util.HashMap;
import java.util.Map;

@Service
public class RejectLoan {

    private final LoanPort loanPort;
    private final TransactionLogService transactionLogService;

    public RejectLoan(LoanPort loanPort, TransactionLogService transactionLogService) {
        this.loanPort = loanPort;
        this.transactionLogService = transactionLogService;
    }

    public BankLoan rejectLoan(int loanId, User analyst) {
        if (analyst == null || analyst.getRoles() != Roles.InternalAnalyst) {
            throw new BusinessException("NO_PERMISOS_RECHAZAR", "No tiene permisos para rechazar préstamos");
        }

        BankLoan loan = loanPort.findById(loanId);
        if (loan == null) {
            throw new NotFoundException("El préstamo no existe");
        }
        if (loan.getLoanState() != LoanState.PENDING) {
            throw new BusinessException("INVALID_STATE", "El préstamo solo puede rechazarse si está en estado PENDING");
        }

        loan.setLoanState(LoanState.REJECTED);
        loan.setAnalystId(analyst.getUserId());
        BankLoan saved = loanPort.save(loan);

        Map<String, Object> detail = new HashMap<>();
        detail.put("previousState", LoanState.PENDING.name());
        detail.put("newState", LoanState.REJECTED.name());
        detail.put("analystId", analyst.getUserId());
        transactionLogService.log(OperationType.LOAN_REJECTED, TransactionType.LOAN_REPAYMENT,
                analyst.getUserId(), analyst.getUsername(), TransactionState.FAILED,
                String.valueOf(loanId), "Préstamo rechazado", detail);

        return saved;
    }
}
