package app.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import app.domain.models.enums.Roles;
import app.domain.models.enums.UserState;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

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
    private String phoneNumber;
    private String address;
    private LocalDate birthDate;
    private Roles roles;
    private UserState userState;
}
