package app.domain.ports;

import java.util.List;

public interface BankingPort {

    public BankingPort saveProduct(BankingPort product);
    public BankingPort getProductByCode(String code);
    public List<BankingPort> getAllProducts();

}