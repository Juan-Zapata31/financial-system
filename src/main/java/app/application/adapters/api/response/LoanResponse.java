package app.application.adapters.api.response;

import app.domain.models.enums.BankLoanType;
import app.domain.models.enums.LoanState;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @AllArgsConstructor
public class LoanResponse {
    private int bankLoanId;
    private BankLoanType bankLoanType;
    private Long clientId;
    private String clientName;
    private BigDecimal requestedAmount;
    private BigDecimal approvedAmount;
    private BigDecimal interestRate;
    private int termMonths;
    private LoanState loanState;
    private LocalDate approvedDate;
    private LocalDate disbursementDate;
    private Integer destinationAccount;
}
