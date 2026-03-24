package app.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseClient extends Client {
    private String companyName;
    private String nit;
    private String legalRepresentative;
}
