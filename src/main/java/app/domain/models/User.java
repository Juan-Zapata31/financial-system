package app.domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import app.domain.models.enums.Roles;
import app.domain.models.enums.UserState;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    private Long userId;
    private String username;
    private String password;
    private Roles roles;
    @Enumerated(EnumType.STRING) // Asegura que el estado se almacene como texto en la base de datos
    private UserState userState;
}
