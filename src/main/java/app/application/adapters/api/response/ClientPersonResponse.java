package app.application.adapters.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ClientPersonResponse {

    private Long clientId;
    private String fullName;
    private String documentNumber;
    private String email;
    private String phone;
    private String address;
    private LocalDate birthDate;
    private Long userId;
    private String username;
}
