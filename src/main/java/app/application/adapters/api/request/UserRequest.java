package app.application.adapters.api.request;

import app.domain.models.enums.Roles;
import app.domain.models.enums.UserState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter; import lombok.NoArgsConstructor; import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UserRequest {
    @NotBlank(message = "El username es obligatorio")
    private String username;
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
    @NotNull(message = "El rol es obligatorio")
    private Roles roles;
    private UserState userState;
    private String companyNit;
}
