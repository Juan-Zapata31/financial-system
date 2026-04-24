package app.domain.models;

import app.domain.models.enums.OperationType;
import app.domain.models.enums.TransactionState;
import app.domain.models.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionLog {
    private String registerId;
    private OperationType operationType;
    private TransactionType transactionType;
    private LocalDateTime operationDateTime;
    private Long responsableUserId;
    private String responsableUser;
    private String description;
    private TransactionState transactionState;
    private String affectedProductId;
    private Map<String, Object> detail;
}
