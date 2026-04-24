package app.application.adapters.api.controllers;

import app.application.adapters.api.request.AccountRequest;
import app.application.adapters.api.request.DepositWithdrawRequest;
import app.application.adapters.api.response.AccountResponse;
import app.application.usecases.TellerUseCase;
import app.domain.models.Account;
import app.domain.models.Client;
import app.domain.ports.ClientPort;
import app.infrastructure.security.FinancialAuthDetails;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teller")
public class TellerController {

    private final TellerUseCase tellerUseCase;
    private final ClientPort clientPort;

    public TellerController(TellerUseCase tellerUseCase, ClientPort clientPort) {
        this.tellerUseCase = tellerUseCase;
        this.clientPort = clientPort;
    }

    @PostMapping("/accounts")
    public ResponseEntity<AccountResponse> openAccount(@Valid @RequestBody AccountRequest request,
                                                        Authentication authentication) {
        FinancialAuthDetails details = (FinancialAuthDetails) authentication.getDetails();
        Account account = toAccount(request);
        Account saved = tellerUseCase.openAccount(account, details.getUserId(), details.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(toAccountResponse(saved));
    }

    @GetMapping("/accounts/{accountNumber}")
    public ResponseEntity<AccountResponse> findAccount(@PathVariable int accountNumber) {
        return ResponseEntity.ok(toAccountResponse(tellerUseCase.findAccount(accountNumber)));
    }

    @GetMapping("/accounts/client/{clientId}")
    public ResponseEntity<List<AccountResponse>> findByClient(@PathVariable Long clientId) {
        List<AccountResponse> accounts = tellerUseCase.findByClientId(clientId)
                .stream().map(TellerController::toAccountResponse).toList();
        return ResponseEntity.ok(accounts);
    }

    @PostMapping("/deposits")
    public ResponseEntity<String> deposit(@Valid @RequestBody DepositWithdrawRequest request,
                                          Authentication authentication) {
        FinancialAuthDetails details = (FinancialAuthDetails) authentication.getDetails();
        tellerUseCase.deposit(request.getAccountNumber(), request.getAmount(),
                details.getUserId(), details.getUsername());
        return ResponseEntity.ok("Depósito realizado correctamente");
    }

    @PostMapping("/withdrawals")
    public ResponseEntity<String> withdraw(@Valid @RequestBody DepositWithdrawRequest request,
                                           Authentication authentication) {
        FinancialAuthDetails details = (FinancialAuthDetails) authentication.getDetails();
        tellerUseCase.withdraw(request.getAccountNumber(), request.getAmount(),
                details.getUserId(), details.getUsername());
        return ResponseEntity.ok("Retiro realizado correctamente");
    }

    // ── Mappers ────────────────────────────────────────────────────────────────

    private Account toAccount(AccountRequest req) {
        Account a = new Account();
        a.setAccountType(req.getAccountType());
        a.setCurrency(req.getCurrency());
        if (req.getClientId() != null) {
            Client client = clientPort.getClientById(req.getClientId());
            a.setClient(client);
        }
        return a;
    }

    static AccountResponse toAccountResponse(Account a) {
        Long clientId = a.getClient() != null ? a.getClient().getClientId() : null;
        String clientName = a.getClient() != null ? a.getClient().getFullName() : null;
        return new AccountResponse(a.getAccountId(), clientId, clientName,
                a.getAccountType(), a.getBalance(), a.getCurrency(),
                a.getAccountState(), a.getCreationDate());
    }
}
