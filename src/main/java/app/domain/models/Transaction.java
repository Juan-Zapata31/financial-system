package app.domain.models;

import app.domain.models.enums.Currency;
import app.domain.models.enums.TransactionState;
import app.domain.models.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private int transactionId;
    private int originAccount;
    private int destinationAccount;
    private BigDecimal amount;
    private Currency currency;
    private TransactionState transactionState;
    private TransactionType transactionType;
    private String description;
    private LocalDateTime createdAt;
    private Long creatorUserId;
    private Long approverUserId;
}
