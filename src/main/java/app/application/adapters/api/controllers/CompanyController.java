package app.application.adapters.api.controllers;

import app.application.adapters.api.request.LoanRequest;
import app.application.adapters.api.request.TransactionRequest;
import app.application.adapters.api.response.AccountResponse;
import app.application.adapters.api.response.LoanResponse;
import app.application.adapters.api.response.TransactionResponse;
import app.application.usecases.CompanyUseCase;
import app.domain.models.BankLoan;
import app.domain.models.Client;
import app.domain.models.Transaction;
import app.domain.models.User;
import app.domain.ports.ClientPort;
import app.domain.ports.UserPort;
import app.infrastructure.security.FinancialAuthDetails;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyUseCase companyUseCase;
    private final UserPort userPort;
    private final ClientPort clientPort;

    public CompanyController(CompanyUseCase companyUseCase, UserPort userPort,
                              ClientPort clientPort) {
        this.companyUseCase = companyUseCase;
        this.userPort = userPort;
        this.clientPort = clientPort;
    }

    // ── Accounts ──────────────────────────────────────────────────────────────

    @GetMapping("/accounts/{clientId}")
    public ResponseEntity<List<AccountResponse>> findCompanyAccounts(@PathVariable Long clientId) {
        List<AccountResponse> accounts = companyUseCase.findCompanyAccounts(clientId)
                .stream().map(TellerController::toAccountResponse).toList();
        return ResponseEntity.ok(accounts);
    }

    // ── Loans ─────────────────────────────────────────────────────────────────

    @GetMapping("/loans/{clientId}")
    public ResponseEntity<List<LoanResponse>> findCompanyLoans(@PathVariable Long clientId) {
        List<LoanResponse> loans = companyUseCase.findCompanyLoans(clientId)
                .stream().map(LoanController::toLoanResponse).toList();
        return ResponseEntity.ok(loans);
    }

    @PostMapping("/loans")
    public ResponseEntity<LoanResponse> requestLoan(@Valid @RequestBody LoanRequest request,
                                                     Authentication authentication) {
        FinancialAuthDetails details = (FinancialAuthDetails) authentication.getDetails();
        User user = userPort.findByUserId(details.getUserId());
        BankLoan loan = toLoan(request);
        BankLoan saved = companyUseCase.requestLoan(loan, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(LoanController.toLoanResponse(saved));
    }

    // ── Transfers (CompanyEmployee creates) ───────────────────────────────────

    @PostMapping("/transactions")
    public ResponseEntity<TransactionResponse> createTransfer(
            @Valid @RequestBody TransactionRequest request,
            Authentication authentication) {
        FinancialAuthDetails details = (FinancialAuthDetails) authentication.getDetails();
        User creator = userPort.findByUserId(details.getUserId());
        Transaction transaction = toTransaction(request);
        Transaction result = companyUseCase.createTransfer(transaction, creator, details.getCompanyNit());
        return ResponseEntity.status(HttpStatus.CREATED).body(toTransactionResponse(result));
    }

    @GetMapping("/transactions/pending")
    public ResponseEntity<List<TransactionResponse>> findPendingTransfers(
            Authentication authentication) {
        FinancialAuthDetails details = (FinancialAuthDetails) authentication.getDetails();
        List<TransactionResponse> transfers = companyUseCase
                .findPendingTransfers(details.getCompanyNit())
                .stream().map(CompanyController::toTransactionResponse).toList();
        return ResponseEntity.ok(transfers);
    }

    // ── Approvals (CompanySupervisor) ─────────────────────────────────────────

    @PostMapping("/approvals/{transferId}/approve")
    public ResponseEntity<String> approveTransfer(@PathVariable int transferId,
                                                   Authentication authentication) {
        FinancialAuthDetails details = (FinancialAuthDetails) authentication.getDetails();
        User supervisor = userPort.findByUserId(details.getUserId());
        companyUseCase.approveTransfer(transferId, supervisor, details.getCompanyNit());
        return ResponseEntity.ok("Transferencia aprobada y ejecutada correctamente");
    }

    @PostMapping("/approvals/{transferId}/reject")
    public ResponseEntity<String> rejectTransfer(@PathVariable int transferId,
                                                  Authentication authentication) {
        FinancialAuthDetails details = (FinancialAuthDetails) authentication.getDetails();
        User supervisor = userPort.findByUserId(details.getUserId());
        companyUseCase.rejectTransfer(transferId, supervisor, details.getCompanyNit());
        return ResponseEntity.ok("Transferencia rechazada correctamente");
    }

    // ── Mappers ────────────────────────────────────────────────────────────────

    private Transaction toTransaction(TransactionRequest req) {
        Transaction t = new Transaction();
        t.setOriginAccount(req.getOriginAccount());
        t.setDestinationAccount(req.getDestinationAccount());
        t.setAmount(req.getAmount());
        t.setCurrency(req.getCurrency());
        t.setDescription(req.getDescription());
        return t;
    }

    static TransactionResponse toTransactionResponse(Transaction t) {
        return new TransactionResponse(t.getTransactionId(), t.getOriginAccount(),
                t.getDestinationAccount(), t.getAmount(), t.getCurrency(),
                t.getTransactionState(), t.getTransactionType(), t.getDescription(),
                t.getCreatedAt(), t.getCreatorUserId(), t.getApproverUserId());
    }

    private BankLoan toLoan(LoanRequest req) {
        BankLoan l = new BankLoan();
        l.setBankLoanType(req.getBankLoanType());
        l.setRequestedAmount(req.getRequestedAmount());
        l.setTermMonths(req.getTermMonths());
        l.setDestinationAccount(req.getDestinationAccount() != null ? req.getDestinationAccount() : 0);
        if (req.getClientId() != null) {
            Client client = clientPort.getClientById(req.getClientId());
            l.setClient(client);
        }
        return l;
    }
}
