package app.application.usecases;

import app.domain.models.BankLoan;
import app.domain.models.TransactionLog;
import app.domain.models.User;
import app.domain.services.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AnalystUseCase {

    private final ApproveLoan approveLoan;
    private final RejectLoan rejectLoan;
    private final DisburseLoan disburseLoan;
    private final FindLoan findLoan;
    private final TransactionLogService transactionLogService;

    public AnalystUseCase(ApproveLoan approveLoan, RejectLoan rejectLoan,
                          DisburseLoan disburseLoan, FindLoan findLoan,
                          TransactionLogService transactionLogService) {
        this.approveLoan = approveLoan;
        this.rejectLoan = rejectLoan;
        this.disburseLoan = disburseLoan;
        this.findLoan = findLoan;
        this.transactionLogService = transactionLogService;
    }

    public BankLoan approveLoan(int loanId, BigDecimal approvedAmount,
                                BigDecimal interestRate, User analyst) {
        return approveLoan.approveLoan(loanId, approvedAmount, interestRate, analyst);
    }

    public BankLoan rejectLoan(int loanId, User analyst) {
        return rejectLoan.rejectLoan(loanId, analyst);
    }

    public void disburseLoan(int loanId, User analyst) {
        disburseLoan.disburseLoan(loanId, analyst);
    }

    public BankLoan findLoanById(int id) { return findLoan.findById(id); }
    public List<BankLoan> findAllLoans() { return findLoan.findAll(); }
    public List<BankLoan> findLoansByClientId(Long clientId) { return findLoan.findByClientId(clientId); }

    public List<TransactionLog> findAllLogs() { return transactionLogService.findAll(); }
    public List<TransactionLog> findLogsByProduct(String productId) {
        return transactionLogService.findByProduct(productId);
    }
}
