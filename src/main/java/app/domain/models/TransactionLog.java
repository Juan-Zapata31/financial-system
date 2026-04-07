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
import app.domain.models.enums.TransactionState;
import app.domain.models.enums.TransactionType;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TransactionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    private int registerId;
    private TransactionType transactionType;
    private String responsableUser;
    private String description;
    @Enumerated(EnumType.STRING) // Asegura que el estado se almacene como texto en la base de datos
    private TransactionState transactionState;
    private Account originAccount;
}
