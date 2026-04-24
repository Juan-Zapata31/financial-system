package app.domain.models;

import app.domain.models.enums.BankLoanType;
import app.domain.models.enums.LoanState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankLoan {
    private int bankLoanId;
    private BankLoanType bankLoanType;
    private Client client;
    private BigDecimal requestedAmount;
    private BigDecimal approvedAmount;
    private BigDecimal interestRate;
    private int termMonths;
    private LoanState loanState;
    private LocalDate approvedDate;
    private LocalDate disbursementDate;
    private int destinationAccount;
    private Long analystId;
}
