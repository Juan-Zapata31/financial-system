package app.application.adapters.persistence.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import app.domain.models.enums.TransactionState;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;

    private int originAccount;
    private int destinationAccount;
    private BigDecimal amount;
    private String currency;
    @Enumerated(EnumType.STRING)
    private TransactionState transactionState;
    private String transactionType;
    private String description;
    private LocalDateTime createdAt;
}