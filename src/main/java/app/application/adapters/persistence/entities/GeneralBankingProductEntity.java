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
@Table(name = "banking_products")
public class GeneralBankingProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idGeneralBankingProduct;
    private String nameGeneralBankingProduct;
    private String categoryGeneralBankingProduct;
    private boolean requiresApproval;
}