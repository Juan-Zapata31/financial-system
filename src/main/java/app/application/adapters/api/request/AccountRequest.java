package app.application.adapters.api.request;

import app.domain.models.enums.AccountType;
import app.domain.models.enums.Currency;
import jakarta.validation.constraints.NotNull;
import lombok.Getter; import lombok.NoArgsConstructor; import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class AccountRequest {
    @NotNull(message = "El tipo de cuenta es obligatorio")
    private AccountType accountType;
    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clientId;
    @NotNull(message = "La moneda es obligatoria")
    private Currency currency;
}
