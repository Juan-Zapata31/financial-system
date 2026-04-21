package app.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseClient extends Client {
    private Long enterpriseClientId;
    private String companyName;
    private String nit;
    private String legalRepresentative;
}
