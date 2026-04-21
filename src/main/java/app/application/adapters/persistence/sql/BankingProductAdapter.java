package app.application.adapters.persistence.sql;

import app.application.adapters.persistence.sql.entities.GeneralBankingProductEntity;
import app.application.adapters.persistence.sql.repositories.BankingProductRepository;
import app.domain.models.GeneralBankingProduct;
import app.domain.ports.BankingPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BankingProductAdapter implements BankingPort {

    private final BankingProductRepository repository;

    public BankingProductAdapter(BankingProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public GeneralBankingProduct save(GeneralBankingProduct product) {
        GeneralBankingProductEntity saved = repository.save(toEntity(product));
        return toDomain(saved);
    }

    @Override
    public GeneralBankingProduct findByCode(String code) {
        return repository.findByNameGeneralBankingProduct(code)
                .map(this::toDomain).orElse(null);
    }

    @Override
    public List<GeneralBankingProduct> findAll() {
        return repository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    private GeneralBankingProductEntity toEntity(GeneralBankingProduct p) {
        GeneralBankingProductEntity e = new GeneralBankingProductEntity();
        e.setIdGeneralBankingProduct(p.getIdGeneralBankingProduct());
        e.setNameGeneralBankingProduct(p.getNameGeneralBankingProduct());
        e.setCategoryGeneralBankingProduct(p.getCategoryGeneralBankingProduct());
        e.setRequiresApproval(p.isRequiresApproval());
        return e;
    }

    private GeneralBankingProduct toDomain(GeneralBankingProductEntity e) {
        GeneralBankingProduct p = new GeneralBankingProduct();
        p.setIdGeneralBankingProduct(e.getIdGeneralBankingProduct());
        p.setNameGeneralBankingProduct(e.getNameGeneralBankingProduct());
        p.setCategoryGeneralBankingProduct(e.getCategoryGeneralBankingProduct());
        p.setRequiresApproval(e.isRequiresApproval());
        return p;
    }
}
