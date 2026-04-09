package app.application.adapters.persistence.entities;

import app.domain.models.enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EmployeeEntity extends UserEntity {

    @Enumerated(EnumType.STRING)
    private Roles typeEmployee;
}