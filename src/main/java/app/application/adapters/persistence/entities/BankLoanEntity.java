package app.application.adapters.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import app.domain.models.enums.LoanState;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bank_loans")
public class BankLoanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bankLoanId;
    private String bankLoanType;
    private int clientId;
    private BigDecimal requestedAmount;
    private BigDecimal approvedAmount;
    private BigDecimal interestRate;
    private int termMonths;
    @Enumerated(EnumType.STRING)
    private LoanState loanState;
    private LocalDate approvedDate;
    private LocalDate disbursementDate;
    private int destinationAccount;
}