package app.application.usecases;

import app.domain.exceptions.ForbiddenException;
import app.domain.models.Account;
import app.domain.models.BankLoan;
import app.domain.models.Transaction;
import app.domain.models.User;
import app.domain.models.enums.TransactionState;
import app.domain.services.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyUseCase {

    private final CreateTransfer createTransfer;
    private final ApproveTransfer approveTransfer;
    private final RejectTransfer rejectTransfer;
    private final FindTransfer findTransfer;
    private final FindAccount findAccount;
    private final FindLoan findLoan;
    private final RequestLoan requestLoan;

    public CompanyUseCase(CreateTransfer createTransfer, ApproveTransfer approveTransfer,
                          RejectTransfer rejectTransfer, FindTransfer findTransfer,
                          FindAccount findAccount, FindLoan findLoan, RequestLoan requestLoan) {
        this.createTransfer = createTransfer;
        this.approveTransfer = approveTransfer;
        this.rejectTransfer = rejectTransfer;
        this.findTransfer = findTransfer;
        this.findAccount = findAccount;
        this.findLoan = findLoan;
        this.requestLoan = requestLoan;
    }

    public Transaction createTransfer(Transaction transaction, User creator, String companyNit) {
        Account origin = findAccount.findByNumber(transaction.getOriginAccount());
        if (origin.getClient() == null ||
                !companyNit.equals(getClientNit(origin))) {
            throw new ForbiddenException("La cuenta de origen no pertenece a su empresa");
        }
        return createTransfer.createTransfer(transaction, creator);
    }

    public void approveTransfer(int transferId, User supervisor, String companyNit) {
        Transaction t = findTransfer.findById(transferId);
        Account origin = findAccount.findByNumber(t.getOriginAccount());
        if (!companyNit.equals(getClientNit(origin))) {
            throw new ForbiddenException("Esta transferencia no pertenece a su empresa");
        }
        approveTransfer.approveTransfer(transferId, supervisor);
    }

    public void rejectTransfer(int transferId, User supervisor, String companyNit) {
        Transaction t = findTransfer.findById(transferId);
        Account origin = findAccount.findByNumber(t.getOriginAccount());
        if (!companyNit.equals(getClientNit(origin))) {
            throw new ForbiddenException("Esta transferencia no pertenece a su empresa");
        }
        rejectTransfer.rejectTransfer(transferId, supervisor);
    }

    public List<Transaction> findPendingTransfers(String companyNit) {
        return findTransfer.findByState(TransactionState.PENDING).stream()
                .filter(t -> {
                    try {
                        Account a = findAccount.findByNumber(t.getOriginAccount());
                        return companyNit.equals(getClientNit(a));
                    } catch (Exception e) { return false; }
                }).collect(Collectors.toList());
    }

    public List<Account> findCompanyAccounts(Long clientId) {
        return findAccount.findByClientId(clientId);
    }

    public List<BankLoan> findCompanyLoans(Long clientId) {
        return findLoan.findByClientId(clientId);
    }

    public BankLoan requestLoan(BankLoan loan, User user) {
        return requestLoan.requestLoan(loan, user.getUserId(), user.getUsername());
    }

    private String getClientNit(Account account) {
        if (account.getClient() instanceof app.domain.models.EnterpriseClient ec) {
            return ec.getNit();
        }
        return "";
    }
}
