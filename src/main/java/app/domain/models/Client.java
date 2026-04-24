package app.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Client {
    private Long clientId;
    private User user;
    private String fullName;
    private String documentNumber;
    private String email;
    private String phone;
    private String address;
}
