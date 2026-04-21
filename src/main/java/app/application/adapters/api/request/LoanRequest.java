package app.application.adapters.api.request;

import app.domain.models.enums.BankLoanType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter; import lombok.NoArgsConstructor; import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor
public class LoanRequest {
    @NotNull(message = "El tipo de préstamo es obligatorio")
    private BankLoanType bankLoanType;
    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clientId;
    @NotNull @Positive(message = "El monto solicitado debe ser mayor a cero")
    private BigDecimal requestedAmount;
    @Positive(message = "El plazo en meses debe ser mayor a cero")
    private int termMonths;
    private Integer destinationAccount;
}
