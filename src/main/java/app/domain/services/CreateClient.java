package app.domain.services;

import app.domain.exceptions.BusinessException;
import app.domain.models.Client;
import app.domain.models.NaturalClient;
import app.domain.models.User;
import app.domain.models.enums.OperationType;
import app.domain.models.enums.TransactionState;
import app.domain.models.enums.TransactionType;
import app.domain.models.enums.UserState;
import app.domain.ports.ClientPort;
import app.domain.ports.UserPort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

@Service
public class CreateClient {

    private final ClientPort clientPort;
    private final UserPort userPort;
    private final TransactionLogService transactionLogService;

    public CreateClient(ClientPort clientPort, UserPort userPort,
                        TransactionLogService transactionLogService) {
        this.clientPort = clientPort;
        this.userPort = userPort;
        this.transactionLogService = transactionLogService;
    }

    public Client createNaturalClient(NaturalClient client, Long requestingUserId,
                                      String requestingUsername) {
        // 1. Validate document uniqueness
        if (clientPort.existsByDocumentNumber(client.getDocumentNumber())) {
            throw new BusinessException("DUPLICATE_DOCUMENT",
                    "Ya existe un cliente con ese número de documento: " + client.getDocumentNumber());
        }

        // 2. Validate age (must be 18+)
        if (client.getBirthDate() == null) {
            throw new BusinessException("BIRTH_DATE_REQUIRED",
                    "La fecha de nacimiento es obligatoria para clientes persona natural");
        }
        int age = Period.between(client.getBirthDate(), LocalDate.now()).getYears();
        if (age < 18) {
            throw new BusinessException("UNDERAGE_CLIENT",
                    "El cliente debe ser mayor de edad (mínimo 18 años). Edad actual: " + age);
        }

        // 3. If a userId is provided, validate it exists and is ACTIVE
        if (client.getUser() != null && client.getUser().getUserId() != null) {
            User user = userPort.findByUserId(client.getUser().getUserId());
            if (user == null) {
                throw new BusinessException("USER_NOT_FOUND",
                        "No existe un usuario del sistema con ID: " + client.getUser().getUserId());
            }
            if (user.getUserState() != UserState.ACTIVE) {
                throw new BusinessException("USER_NOT_ACTIVE",
                        "El usuario del sistema asociado no está activo");
            }
            client.setUser(user);
        }

        // 4. Save
        Client saved = clientPort.saveClient(client);

        // 5. Audit log
        Map<String, Object> detail = new HashMap<>();
        detail.put("clientId", saved.getClientId());
        detail.put("documentNumber", client.getDocumentNumber());
        detail.put("fullName", client.getFullName());
        detail.put("clientType", "NaturalClient");
        detail.put("birthDate", client.getBirthDate().toString());
        transactionLogService.log(
                OperationType.USER_CREATED,
                TransactionType.PAYMENT,
                requestingUserId,
                requestingUsername,
                TransactionState.COMPLETED,
                String.valueOf(saved.getClientId()),
                "Cliente persona natural registrado",
                detail
        );

        return saved;
    }
}
