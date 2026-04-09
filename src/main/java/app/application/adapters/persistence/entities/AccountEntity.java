package app.application.adapters.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import app.domain.models.enums.AccountState;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;
    private Long clientId;
    private String accountType;
    private BigDecimal balance;
    private String currency;
    @Enumerated(EnumType.STRING)
    private AccountState accountState;
    private LocalDate creationDate;
}