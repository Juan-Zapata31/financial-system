package app.application.adapters.persistence.sql.entities;

import app.domain.models.enums.BankLoanType;
import app.domain.models.enums.LoanState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "bank_loans")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BankLoanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bankLoanId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BankLoanType bankLoanType;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;

    @Column(nullable = false)
    private BigDecimal requestedAmount;

    private BigDecimal approvedAmount;
    private BigDecimal interestRate;

    @Column(nullable = false)
    private Integer termMonths;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanState loanState;

    private LocalDate approvedDate;
    private LocalDate disbursementDate;
    private Integer destinationAccount;
    private Long analystId;
}
