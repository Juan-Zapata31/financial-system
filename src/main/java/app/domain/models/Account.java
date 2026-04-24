package app.domain.models;

import app.domain.models.enums.AccountState;
import app.domain.models.enums.AccountType;
import app.domain.models.enums.Currency;
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
public class Account {
    private int accountId;
    private Client client;
    private AccountType accountType;
    private BigDecimal balance;
    private Currency currency;
    private AccountState accountState;
    private LocalDate creationDate;
}
