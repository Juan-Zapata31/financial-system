package app.domain.ports;

import app.domain.models.GeneralBankingProduct;
import java.util.List;

public interface BankingPort {
    GeneralBankingProduct save(GeneralBankingProduct product);
    GeneralBankingProduct findByCode(String code);
    List<GeneralBankingProduct> findAll();
}
