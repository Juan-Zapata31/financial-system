package app.application.adapters.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import app.domain.models.enums.AccountState;
import app.domain.models.enums.AccountType;
import app.domain.models.enums.Currency;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountId;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @Enumerated(EnumType.STRING)
    private AccountState accountState;
    private LocalDate creationDate;
}