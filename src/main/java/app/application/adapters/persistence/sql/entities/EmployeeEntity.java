package app.application.adapters.persistence.sql.entities;

import app.domain.models.enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employees")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EmployeeEntity extends UserEntity {

    @Enumerated(EnumType.STRING)
    private Roles typeEmployee;
}
