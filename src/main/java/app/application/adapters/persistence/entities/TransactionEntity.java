package app.application.adapters.persistence.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import app.domain.models.enums.Currency;
import app.domain.models.enums.TransactionState;
import app.domain.models.enums.TransactionType;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;
    private Integer originAccount;
    private Integer destinationAccount;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @Enumerated(EnumType.STRING)
    private TransactionState transactionState;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private String description;
    private LocalDateTime createdAt;
}