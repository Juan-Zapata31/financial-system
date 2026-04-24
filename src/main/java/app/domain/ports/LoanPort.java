package app.domain.ports;

import app.domain.models.BankLoan;
import java.util.List;

public interface LoanPort {
    BankLoan save(BankLoan loan);
    BankLoan findById(int loanId);
    List<BankLoan> findByClientId(Long clientId);
    List<BankLoan> findAll();
}
