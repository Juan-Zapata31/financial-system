package app.domain.models;

import app.domain.models.enums.Roles;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Employee extends User {
    private Long employeeId;
    private Roles typeEmployee;
}
