package app.application.adapters.api.response;

import app.domain.models.enums.Roles;
import app.domain.models.enums.UserState;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class UserResponse {
    private Long userId;
    private String username;
    private Roles roles;
    private UserState userState;
    private String companyNit;
}
