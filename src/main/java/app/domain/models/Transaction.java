package app.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import app.domain.models.enums.TransactionState;
import app.domain.models.enums.TransactionType;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction{
    private int transactionId;
    private int originAccount;
    private int destinationAccount;
    private BigDecimal amount;
    private Currency currency;
    private TransactionState transactionState;
    private TransactionType transactionType;
    private String description;
    private LocalDateTime createdAt;
}