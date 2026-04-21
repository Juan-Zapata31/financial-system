package app.application.adapters.persistence.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import app.domain.models.enums.TransactionType;
import app.domain.models.enums.TransactionState;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction_logs")
public class TransactionLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int registerId;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private String responsableUser;
    private String description;
    @Enumerated(EnumType.STRING)
    private TransactionState transactionState;
    private String affectedProductId;
    private LocalDateTime createdAt;
}