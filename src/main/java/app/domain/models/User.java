package app.domain.models;

import app.domain.models.enums.Roles;
import app.domain.models.enums.UserState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long userId;
    private String username;
    private String password;
    private Roles roles;
    private UserState userState;
    private String companyNit;
}
