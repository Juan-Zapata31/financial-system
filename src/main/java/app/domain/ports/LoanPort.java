package app.domain.ports;

import java.util.List;

public interface LoanPort {

    public LoanPort requestLoan(LoanPort loan);
    public LoanPort getLoanById(String loanId);
    public List<LoanPort> getLoansByClient(String clientId);
    public void approveLoan(String loanId);
    public void rejectLoan(String loanId);

}