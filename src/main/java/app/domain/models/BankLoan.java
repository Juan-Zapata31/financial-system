package app.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

import app.domain.models.enums.BankLoanType;
import app.domain.models.enums.LoanState;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BankLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    private int bankLoanId;
    private BankLoanType bankLoanType;
    private int clientId;
    private BigDecimal requestedAmount;
    private BigDecimal approvedAmount;
    private BigDecimal interestRate;
    private int termMonths;

    @Enumerated(EnumType.STRING) // Asegura que el estado se almacene como texto en la base de datos
    private LoanState loanState;

    private LocalDate approvedDate;
    private LocalDate disbursementDate;
    private int destinationAccount;
}
