package app.application.adapters.persistence.sql.repositories;

import app.application.adapters.persistence.sql.entities.GeneralBankingProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankingProductRepository extends JpaRepository<GeneralBankingProductEntity, Integer> {
    Optional<GeneralBankingProductEntity> findByNameGeneralBankingProduct(String name);
}
