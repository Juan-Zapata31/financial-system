package app.application.adapters.persistence.mongodb.documents;

import app.domain.models.enums.OperationType;
import app.domain.models.enums.TransactionState;
import app.domain.models.enums.TransactionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "transaction_logs")
@Getter @Setter @NoArgsConstructor
public class TransactionLogDocument {

    @Id
    private String registerId;

    @Field("operation_type")
    private OperationType operationType;

    @Field("transaction_type")
    private TransactionType transactionType;

    @Field("operation_date_time")
    private LocalDateTime operationDateTime;

    @Field("responsable_user_id")
    private Long responsableUserId;

    @Field("responsable_user")
    private String responsableUser;

    @Field("description")
    private String description;

    @Field("transaction_state")
    private TransactionState transactionState;

    @Field("affected_product_id")
    private String affectedProductId;

    @Field("detail")
    private Map<String, Object> detail;
}
