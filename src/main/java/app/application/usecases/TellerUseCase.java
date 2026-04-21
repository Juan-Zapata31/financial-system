package app.application.usecases;

import app.domain.models.Account;
import app.domain.services.DepositWithdraw;
import app.domain.services.FindAccount;
import app.domain.services.OpenAccount;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TellerUseCase {

    private final OpenAccount openAccount;
    private final DepositWithdraw depositWithdraw;
    private final FindAccount findAccount;

    public TellerUseCase(OpenAccount openAccount, DepositWithdraw depositWithdraw,
                         FindAccount findAccount) {
        this.openAccount = openAccount;
        this.depositWithdraw = depositWithdraw;
        this.findAccount = findAccount;
    }

    public Account openAccount(Account account, Long tellerId, String tellerUsername) {
        return openAccount.openAccount(account, tellerId, tellerUsername);
    }

    public void deposit(int accountNumber, BigDecimal amount, Long tellerId, String username) {
        depositWithdraw.deposit(accountNumber, amount, tellerId, username);
    }

    public void withdraw(int accountNumber, BigDecimal amount, Long tellerId, String username) {
        depositWithdraw.withdraw(accountNumber, amount, tellerId, username);
    }

    public Account findAccount(int accountNumber) { return findAccount.findByNumber(accountNumber); }
    public List<Account> findByClientId(Long clientId) { return findAccount.findByClientId(clientId); }
}
