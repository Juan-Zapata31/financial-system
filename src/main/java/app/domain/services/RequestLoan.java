package app.domain.services;

import app.domain.exceptions.BusinessException;
import app.domain.models.BankLoan;
import app.domain.models.enums.LoanState;
import app.domain.models.enums.OperationType;
import app.domain.models.enums.TransactionState;
import app.domain.models.enums.TransactionType;
import app.domain.ports.ClientPort;
import app.domain.ports.LoanPort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class RequestLoan {

    private final LoanPort loanPort;
    private final ClientPort clientPort;
    private final TransactionLogService transactionLogService;

    public RequestLoan(LoanPort loanPort, ClientPort clientPort,
                       TransactionLogService transactionLogService) {
        this.loanPort = loanPort;
        this.clientPort = clientPort;
        this.transactionLogService = transactionLogService;
    }

    public BankLoan requestLoan(BankLoan loan, Long requestingUserId, String requestingUsername) {
        if (loan.getClient() == null) {
            throw new BusinessException("CLIENT_REQUIRED", "El cliente es obligatorio");
        }
        if (clientPort.getClientById(loan.getClient().getClientId()) == null) {
            throw new BusinessException("CLIENT_NOT_FOUND", "El cliente no existe en el sistema");
        }
        if (loan.getRequestedAmount() == null ||
                loan.getRequestedAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("INVALID_AMOUNT", "El monto solicitado debe ser mayor a cero");
        }
        if (loan.getTermMonths() <= 0) {
            throw new BusinessException("INVALID_TERM", "El plazo en meses debe ser mayor a cero");
        }

        loan.setLoanState(LoanState.PENDING);
        BankLoan saved = loanPort.save(loan);

        Map<String, Object> detail = new HashMap<>();
        detail.put("clientId", loan.getClient().getClientId());
        detail.put("requestedAmount", loan.getRequestedAmount());
        detail.put("bankLoanType", loan.getBankLoanType().name());
        detail.put("termMonths", loan.getTermMonths());
        detail.put("loanState", LoanState.PENDING.name());
        transactionLogService.log(OperationType.LOAN_REQUESTED, TransactionType.LOAN_REPAYMENT,
                requestingUserId, requestingUsername, TransactionState.PENDING,
                String.valueOf(saved.getBankLoanId()), "Solicitud de préstamo creada", detail);

        return saved;
    }
}
