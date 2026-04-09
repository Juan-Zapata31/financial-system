package app.application.adapters.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private String transactionType;
    private String responsableUser;
    private String description;
    @Enumerated(EnumType.STRING)
    private TransactionState transactionState;
    private int originAccount;
}