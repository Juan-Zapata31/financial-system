package app.application.adapters.persistence.sql.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "natural_clients")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class NaturalClientEntity extends ClientEntity {

    @Column(nullable = false)
    private LocalDate birthDate;
}
