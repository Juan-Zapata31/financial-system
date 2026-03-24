package app.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

import app.domain.models.enums.BankLoanType;
import app.domain.models.enums.LoanState;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankLoan {
    private int bankLoanId;
    private BankLoanType bankLoanType;
    private int applicantId; //Modificar cuando cree el cliente
    private BigDecimal requestedAmount;
    private BigDecimal approvedAmount;
    private BigDecimal interestRate;
    private int termMonths;
    private LoanState loanState;
    private LocalDate approvedDate;
    private LocalDate disbursementDate;
    private Account destinationAccount;
}
