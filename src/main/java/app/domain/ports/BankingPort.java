package app.domain.ports;

import java.util.List;

import app.domain.models.GeneralBankingProduct;

public interface BankingPort {

    GeneralBankingProduct save(GeneralBankingProduct product);
    GeneralBankingProduct findByCode(String code);
    List<GeneralBankingProduct> findAll();

}