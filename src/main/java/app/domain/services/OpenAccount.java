package app.domain.services;

import app.domain.exceptions.BusinessException;
import app.domain.exceptions.NotFoundException;
import app.domain.models.Account;
import app.domain.models.enums.AccountState;
import app.domain.models.enums.OperationType;
import app.domain.models.enums.TransactionState;
import app.domain.models.enums.TransactionType;
import app.domain.models.enums.UserState;
import app.domain.models.User;
import app.domain.ports.AccountPort;
import app.domain.ports.ClientPort;
import app.domain.ports.UserPort;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class OpenAccount {

    private final AccountPort accountPort;
    private final ClientPort clientPort;
    private final UserPort userPort;
    private final TransactionLogService transactionLogService;

    public OpenAccount(AccountPort accountPort, ClientPort clientPort,
            UserPort userPort, TransactionLogService transactionLogService) {
        this.accountPort = accountPort;
        this.clientPort = clientPort;
        this.userPort = userPort;
        this.transactionLogService = transactionLogService;
    }

    public Account openAccount(Account account, Long requestingUserId, String requestingUsername) {
        if (account.getClient() == null || account.getClient().getClientId() == null) {
            throw new BusinessException("CLIENT_REQUIRED", "El titular de la cuenta es obligatorio");
        }

        var client = clientPort.getClientById(account.getClient().getClientId());
        if (client == null) {
            throw new NotFoundException("El cliente no existe en el sistema");
        }

        // Validate client user is ACTIVE
        if (client.getUser() != null) {
            User clientUser = userPort.findByUserId(client.getUser().getUserId());
            if (clientUser != null && clientUser.getUserState() != UserState.ACTIVE) {
                throw new BusinessException("USER_NOT_ACTIVE",
                        "No se puede abrir una cuenta a un usuario inactivo o bloqueado");
            }
        }

        account.setAccountState(AccountState.ACTIVE);
        account.setCreationDate(LocalDate.now());
        account.setBalance(BigDecimal.ZERO);
        account.setClient(client);

        Account saved = accountPort.save(account);

        Map<String, Object> detail = new HashMap<>();
        detail.put("clientId", client.getClientId());
        detail.put("accountType", account.getAccountType().name());
        detail.put("currency", account.getCurrency().name());
        transactionLogService.log(OperationType.ACCOUNT_OPENED, TransactionType.DEPOSIT,
                requestingUserId, requestingUsername, TransactionState.COMPLETED,
                String.valueOf(saved.getAccountId()), "Cuenta abierta", detail);

        return saved;
    }
}
