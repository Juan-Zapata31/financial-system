package app.application.adapters.persistence.entities;

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
@Table(name = "enterprise_clients")
public class EnterpriseClientEntity extends ClientEntity {

    private String companyName;
    private String nit;
    private String legalRepresentative;
}