package app.domain.models;

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
public class TransactionLog {
    private int registerId;
    private TransactionType transactionType;
    private String responsableUser;
    private String description;
    private TransactionState transactionState;
    private Account originAccount;
}
