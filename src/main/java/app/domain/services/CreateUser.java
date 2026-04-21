package app.domain.services;

import app.domain.exceptions.BusinessException;
import app.domain.models.User;
import app.domain.models.enums.OperationType;
import app.domain.models.enums.TransactionState;
import app.domain.models.enums.TransactionType;
import app.domain.ports.UserPort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CreateUser {

    private final UserPort userPort;
    private final TransactionLogService transactionLogService;

    public CreateUser(UserPort userPort, TransactionLogService transactionLogService) {
        this.userPort = userPort;
        this.transactionLogService = transactionLogService;
    }

    public void createUser(User user) {
        if (userPort.existsByUsername(user.getUsername())) {
            throw new BusinessException("DUPLICATE_USERNAME", "Ya existe un usuario con ese nombre de usuario");
        }
        userPort.save(user);

        Map<String, Object> detail = new HashMap<>();
        detail.put("username", user.getUsername());
        detail.put("role", user.getRoles().name());
        transactionLogService.log(OperationType.USER_CREATED, TransactionType.PAYMENT,
                user.getUserId(), user.getUsername(), TransactionState.COMPLETED,
                String.valueOf(user.getUserId()), "Usuario creado", detail);
    }
}
