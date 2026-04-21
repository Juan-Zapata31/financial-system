package app.application.adapters.api.request;

import app.domain.models.enums.Currency;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter; import lombok.NoArgsConstructor; import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor
public class TransactionRequest {
    @NotNull(message = "La cuenta de origen es obligatoria")
    private Integer originAccount;
    @NotNull(message = "La cuenta de destino es obligatoria")
    private Integer destinationAccount;
    @NotNull @Positive(message = "El monto debe ser mayor a cero")
    private BigDecimal amount;
    private Currency currency;
    private String description;
}
