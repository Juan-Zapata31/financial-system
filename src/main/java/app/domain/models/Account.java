package app.domain.models;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import app.domain.models.enums.AccountState;
import app.domain.models.enums.AccountType;
import app.domain.models.enums.Currency;
import lombok.AllArgsConstructor;

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
