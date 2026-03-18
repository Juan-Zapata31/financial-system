package app.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeneralBankingProduct {
    private int idGeneralBankingProduct;
    private String nameGeneralBankingProduct;
    private String categoryGeneralBankingProduct;
    private boolean RequiresApproval;
}
