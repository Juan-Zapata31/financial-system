package app.domain.services;

import app.domain.exceptions.BusinessException;
import app.domain.exceptions.NotFoundException;
import app.domain.models.Account;
import app.domain.models.enums.AccountState;
import app.domain.models.enums.OperationType;
import app.domain.models.enums.TransactionState;
import app.domain.models.enums.TransactionType;
import app.domain.ports.AccountPort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class DepositWithdraw {

    private final AccountPort accountPort;
    private final TransactionLogService transactionLogService;

    public DepositWithdraw(AccountPort accountPort,
                           TransactionLogService transactionLogService) {
        this.accountPort = accountPort;
        this.transactionLogService = transactionLogService;
    }

    public void deposit(int accountNumber, BigDecimal amount, Long tellerId, String tellerUsername) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("INVALID_AMOUNT", "El monto del depósito debe ser mayor a cero");
        }
        Account account = getActiveAccount(accountNumber);
        BigDecimal balanceBefore = account.getBalance();
        account.setBalance(account.getBalance().add(amount));
        accountPort.save(account);

        Map<String, Object> detail = new HashMap<>();
        detail.put("amount", amount);
        detail.put("balanceBefore", balanceBefore);
        detail.put("balanceAfter", account.getBalance());
        transactionLogService.log(OperationType.DEPOSIT, TransactionType.DEPOSIT,
                tellerId, tellerUsername, TransactionState.COMPLETED,
                String.valueOf(accountNumber), "Depósito realizado", detail);
    }

    public void withdraw(int accountNumber, BigDecimal amount, Long tellerId, String tellerUsername) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("INVALID_AMOUNT", "El monto del retiro debe ser mayor a cero");
        }
        Account account = getActiveAccount(accountNumber);
        if (account.getBalance().compareTo(amount) < 0) {
            throw new BusinessException("INSUFFICIENT_FUNDS", "Saldo insuficiente para realizar el retiro");
        }
        BigDecimal balanceBefore = account.getBalance();
        account.setBalance(account.getBalance().subtract(amount));
        accountPort.save(account);

        Map<String, Object> detail = new HashMap<>();
        detail.put("amount", amount);
        detail.put("balanceBefore", balanceBefore);
        detail.put("balanceAfter", account.getBalance());
        transactionLogService.log(OperationType.WITHDRAWAL, TransactionType.WITHDRAWAL,
                tellerId, tellerUsername, TransactionState.COMPLETED,
                String.valueOf(accountNumber), "Retiro realizado", detail);
    }

    private Account getActiveAccount(int accountNumber) {
        Account account = accountPort.findByNumber(accountNumber);
        if (account == null) {
            throw new NotFoundException("No existe una cuenta con ese número");
        }
        if (account.getAccountState() == AccountState.BLOCKED ||
                account.getAccountState() == AccountState.CANCELLED) {
            throw new BusinessException("ACCOUNT_NOT_OPERABLE",
                    "La cuenta está bloqueada o cancelada. No se pueden realizar operaciones");
        }
        return account;
    }
}
