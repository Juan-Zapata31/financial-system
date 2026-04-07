package app.domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import app.domain.models.enums.AccountState;
import app.domain.models.enums.AccountType;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    private int accountId;
    private Client client;
    private AccountType accountType;
    private BigDecimal balance;
    private Currency currency;
    @Enumerated(EnumType.STRING) // Asegura que el estado se almacene como texto en la base de datos
    private AccountState accountState;
    private LocalDate creationDate;
}
