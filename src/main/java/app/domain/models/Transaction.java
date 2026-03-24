package app.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Currency;

import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction{
    private int transactionId;
    private Account accountOrigin;
    private Account accountDestination;
    private BigDecimal amount;
    private Currency currency;
    private boolean statusTrasaction;
    private String typeTransaction;
    private String descriptionTransaction;
}