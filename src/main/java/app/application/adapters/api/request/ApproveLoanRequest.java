package app.application.adapters.api.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter; import lombok.NoArgsConstructor; import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor
public class ApproveLoanRequest {
    @NotNull @Positive(message = "El monto aprobado debe ser mayor a cero")
    private BigDecimal approvedAmount;
    @NotNull @Positive(message = "La tasa de interés debe ser mayor a cero")
    private BigDecimal interestRate;
}
