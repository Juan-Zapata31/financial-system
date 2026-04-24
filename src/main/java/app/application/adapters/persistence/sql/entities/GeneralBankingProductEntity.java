package app.application.adapters.persistence.sql.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "banking_products")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class GeneralBankingProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idGeneralBankingProduct;

    @Column(nullable = false)
    private String nameGeneralBankingProduct;

    @Column(nullable = false)
    private String categoryGeneralBankingProduct;

    @Column(nullable = false)
    private boolean requiresApproval;
}
