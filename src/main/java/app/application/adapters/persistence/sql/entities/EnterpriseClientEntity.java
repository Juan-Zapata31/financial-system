package app.application.adapters.persistence.sql.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "enterprise_clients")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EnterpriseClientEntity extends ClientEntity {

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false, unique = true)
    private String nit;

    @Column(nullable = false)
    private String legalRepresentative;
}
