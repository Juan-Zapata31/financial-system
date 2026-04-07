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
import java.time.LocalDateTime;
import java.util.Currency;

import app.domain.models.enums.TransactionState;
import app.domain.models.enums.TransactionType;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    private int transactionId;
    private int originAccount;
    private int destinationAccount;
    private BigDecimal amount;
    private Currency currency;
    @Enumerated(EnumType.STRING) // Asegura que el estado se almacene como texto en la base de datos
    private TransactionState transactionState;
    private TransactionType transactionType;
    private String description;
    private LocalDateTime createdAt;
}