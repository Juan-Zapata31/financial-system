package app.domain.models;

import app.domain.models.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends User {
    private Long employeeId;
    private Roles typeEmployee;
}
