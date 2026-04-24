package app.application.adapters.api.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter; import lombok.NoArgsConstructor; import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor
public class DepositWithdrawRequest {
    @NotNull
    private Integer accountNumber;
    @NotNull @Positive(message = "El monto debe ser mayor a cero")
    private BigDecimal amount;
}
