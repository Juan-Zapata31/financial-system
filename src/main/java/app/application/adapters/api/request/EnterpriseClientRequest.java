package app.application.adapters.api.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EnterpriseClientRequest {

    @NotBlank(message = "La razón social es obligatoria")
    private String companyName;

    @NotBlank(message = "El NIT es obligatorio")
    private String nit;

    @NotBlank(message = "El correo corporativo es obligatorio")
    @Email(message = "El correo electrónico no tiene un formato válido")
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(min = 7, max = 15, message = "El teléfono debe tener entre 7 y 15 dígitos")
    private String phone;

    @NotBlank(message = "La dirección es obligatoria")
    private String address;

    @NotBlank(message = "El documento del representante legal es obligatorio")
    private String legalRepresentative;

    // Full name of the enterprise (as document number in the client table)
    @NotBlank(message = "El nombre de la empresa es obligatorio")
    private String fullName;

    // Optional: link to a user account
    private Long userId;
}
