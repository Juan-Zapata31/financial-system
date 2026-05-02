package app.application.adapters.api.controllers;

import app.application.adapters.api.request.ClientPersonRequest;
import app.application.adapters.api.request.LoanRequest;
import app.application.adapters.api.response.ClientPersonResponse;
import app.application.adapters.api.response.LoanResponse;
import app.application.usecases.CommercialUseCase;
import app.domain.models.BankLoan;
import app.domain.models.Client;
import app.domain.models.NaturalClient;
import app.domain.models.User;
import app.domain.ports.ClientPort;
import app.domain.ports.UserPort;
import app.infrastructure.security.FinancialAuthDetails;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import app.application.adapters.api.request.EnterpriseClientRequest;
import app.domain.models.EnterpriseClient;

import java.util.List;

@RestController
@RequestMapping("/commercial")
public class CommercialController {

    private final CommercialUseCase commercialUseCase;
    private final UserPort userPort;
    private final ClientPort clientPort;

    public CommercialController(CommercialUseCase commercialUseCase,
            UserPort userPort, ClientPort clientPort) {
        this.commercialUseCase = commercialUseCase;
        this.userPort = userPort;
        this.clientPort = clientPort;
    }

    // ── Clients ───────────────────────────────────────────────────────────────

    @PostMapping("/clients/natural")
    public ResponseEntity<ClientPersonResponse> createNaturalClient(
            @Valid @RequestBody ClientPersonRequest request,
            Authentication authentication) {

        FinancialAuthDetails details = (FinancialAuthDetails) authentication.getDetails();
        NaturalClient client = toNaturalClient(request);
        Client saved = commercialUseCase.createNaturalClient(
                client, details.getUserId(), details.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toClientResponse(saved));
    }

    @PostMapping("/clients/enterprise")
    public ResponseEntity<ClientPersonResponse> createEnterpriseClient(
            @Valid @RequestBody EnterpriseClientRequest request,
            Authentication authentication) {

        FinancialAuthDetails details = (FinancialAuthDetails) authentication.getDetails();
        EnterpriseClient client = toEnterpriseClient(request);
        Client saved = commercialUseCase.createEnterpriseClient(
                client, details.getUserId(), details.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toClientResponse(saved));
    }

    @GetMapping("/clients/{clientId}")
    public ResponseEntity<ClientPersonResponse> findClientById(@PathVariable Long clientId) {
        Client client = commercialUseCase.findClientById(clientId);
        return ResponseEntity.ok(toClientResponse(client));
    }

    @GetMapping("/clients/document/{documentNumber}")
    public ResponseEntity<ClientPersonResponse> findClientByDocument(
            @PathVariable String documentNumber) {
        Client client = commercialUseCase.findClientByDocument(documentNumber);
        return ResponseEntity.ok(toClientResponse(client));
    }

    @GetMapping("/clients")
    public ResponseEntity<List<ClientPersonResponse>> findAllClients() {
        List<ClientPersonResponse> clients = commercialUseCase.findAllClients()
                .stream()
                .map(CommercialController::toClientResponse)
                .toList();
        return ResponseEntity.ok(clients);
    }

    // ── Loans ─────────────────────────────────────────────────────────────────

    @PostMapping("/loans")
    public ResponseEntity<LoanResponse> requestLoan(
            @Valid @RequestBody LoanRequest request,
            Authentication authentication) {
        FinancialAuthDetails details = (FinancialAuthDetails) authentication.getDetails();
        User commercial = userPort.findByUserId(details.getUserId());
        BankLoan loan = toLoan(request);
        BankLoan saved = commercialUseCase.requestLoan(loan, commercial);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(LoanController.toLoanResponse(saved));
    }

    @GetMapping("/loans/client/{clientId}")
    public ResponseEntity<List<LoanResponse>> findLoansByClient(@PathVariable Long clientId) {
        List<LoanResponse> loans = commercialUseCase.findLoansByClientId(clientId)
                .stream().map(LoanController::toLoanResponse).toList();
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/loans/{id}")
    public ResponseEntity<LoanResponse> findLoanById(@PathVariable int id) {
        return ResponseEntity.ok(LoanController.toLoanResponse(
                commercialUseCase.findLoanById(id)));
    }

    // ── Mappers ────────────────────────────────────────────────────────────────

    private static NaturalClient toNaturalClient(ClientPersonRequest req) {
        NaturalClient client = new NaturalClient();
        client.setFullName(req.getFullName());
        client.setDocumentNumber(req.getDocumentNumber());
        client.setEmail(req.getEmail());
        client.setPhone(req.getPhone());
        client.setAddress(req.getAddress());
        client.setBirthDate(req.getBirthDate());
        if (req.getUserId() != null) {
            User user = new User();
            user.setUserId(req.getUserId());
            client.setUser(user);
        }
        return client;
    }

    private static EnterpriseClient toEnterpriseClient(EnterpriseClientRequest req) {
        EnterpriseClient client = new EnterpriseClient();
        client.setFullName(req.getFullName());
        client.setDocumentNumber(req.getNit());
        client.setEmail(req.getEmail());
        client.setPhone(req.getPhone());
        client.setAddress(req.getAddress());
        client.setCompanyName(req.getCompanyName());
        client.setNit(req.getNit());
        client.setLegalRepresentative(req.getLegalRepresentative());
        if (req.getUserId() != null) {
            User user = new User();
            user.setUserId(req.getUserId());
            client.setUser(user);
        }
        return client;
    }

    // Safe mapper - works for both NaturalClient and EnterpriseClient
    static ClientPersonResponse toClientResponse(Client client) {
        Long userId = client.getUser() != null ? client.getUser().getUserId() : null;
        String username = client.getUser() != null ? client.getUser().getUsername() : null;
        String documentNumber = client.getDocumentNumber();
        String fullName = client.getFullName();

        java.time.LocalDate birthDate = null;
        if (client instanceof NaturalClient nc) {
            birthDate = nc.getBirthDate();
        }

        return new ClientPersonResponse(
                client.getClientId(),
                fullName,
                documentNumber,
                client.getEmail(),
                client.getPhone(),
                client.getAddress(),
                birthDate,
                userId,
                username);
    }

    private BankLoan toLoan(LoanRequest req) {
        BankLoan l = new BankLoan();
        l.setBankLoanType(req.getBankLoanType());
        l.setRequestedAmount(req.getRequestedAmount());
        l.setTermMonths(req.getTermMonths());
        l.setDestinationAccount(req.getDestinationAccount() != null
                ? req.getDestinationAccount()
                : 0);
        if (req.getClientId() != null) {
            Client client = clientPort.getClientById(req.getClientId());
            l.setClient(client);
        }
        return l;
    }
}
