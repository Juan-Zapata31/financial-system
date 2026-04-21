package app.application.adapters.api.response;

import app.domain.models.enums.AccountState;
import app.domain.models.enums.AccountType;
import app.domain.models.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @AllArgsConstructor
public class AccountResponse {
    private int accountId;
    private Long clientId;
    private String clientName;
    private AccountType accountType;
    private BigDecimal balance;
    private Currency currency;
    private AccountState accountState;
    private LocalDate creationDate;
}
