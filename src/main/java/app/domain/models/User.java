package app.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {
    private int idUser;
    private int idRegister;
    private String fullName;
    private int idIdentityCard;
    private String email;
    private int phoneNumber;
    private String address;
    private String bornDate;
    private String Role;
    private Boolean isActive;
}
