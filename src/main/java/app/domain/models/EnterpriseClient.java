package app.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseClient extends User {
    private String companyName;
    private int nit;
    private String legalRepresentative;
}
