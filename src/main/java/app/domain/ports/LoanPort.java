package app.domain.ports;

import java.util.List;

import app.domain.models.BankLoan;

public interface LoanPort {

    BankLoan save(BankLoan loan);
    BankLoan findById(String loanId);
    List<BankLoan> findByClientId(String bankLoanId);
}