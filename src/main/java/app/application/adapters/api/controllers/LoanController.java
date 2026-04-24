package app.application.adapters.api.controllers;

import app.application.adapters.api.request.ApproveLoanRequest;
import app.application.adapters.api.request.LoanRequest;
import app.application.adapters.api.response.LoanResponse;
import app.application.usecases.AnalystUseCase;
import app.application.usecases.CommercialUseCase;
import app.domain.models.BankLoan;
import app.domain.models.Client;
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
@RequestMapping("/loans")
public class LoanController {

    private final AnalystUseCase analystUseCase;
    private final CommercialUseCase commercialUseCase;
    private final UserPort userPort;
    private final ClientPort clientPort;

    public LoanController(AnalystUseCase analystUseCase, CommercialUseCase commercialUseCase,
                          UserPort userPort, ClientPort clientPort) {
        this.analystUseCase = analystUseCase;
        this.commercialUseCase = commercialUseCase;
        this.userPort = userPort;
        this.clientPort = clientPort;
    }

    // CommercialEmployee or IndividualClient requests a loan
    @PostMapping
    public ResponseEntity<LoanResponse> requestLoan(@Valid @RequestBody LoanRequest request,
                                                     Authentication authentication) {
        FinancialAuthDetails details = (FinancialAuthDetails) authentication.getDetails();
        User requester = userPort.findByUserId(details.getUserId());
        BankLoan loan = toLoan(request);
        BankLoan saved = commercialUseCase.requestLoan(loan, requester);
        return ResponseEntity.status(HttpStatus.CREATED).body(toLoanResponse(saved));
    }

    // InternalAnalyst approves
    @PostMapping("/{id}/approve")
    public ResponseEntity<LoanResponse> approveLoan(@PathVariable int id,
                                                     @Valid @RequestBody ApproveLoanRequest request,
                                                     Authentication authentication) {
        FinancialAuthDetails details = (FinancialAuthDetails) authentication.getDetails();
        User analyst = userPort.findByUserId(details.getUserId());
        BankLoan saved = analystUseCase.approveLoan(id, request.getApprovedAmount(),
                request.getInterestRate(), analyst);
        return ResponseEntity.ok(toLoanResponse(saved));
    }

    // InternalAnalyst rejects
    @PostMapping("/{id}/reject")
    public ResponseEntity<LoanResponse> rejectLoan(@PathVariable int id,
                                                    Authentication authentication) {
        FinancialAuthDetails details = (FinancialAuthDetails) authentication.getDetails();
        User analyst = userPort.findByUserId(details.getUserId());
        BankLoan saved = analystUseCase.rejectLoan(id, analyst);
        return ResponseEntity.ok(toLoanResponse(saved));
    }

    // InternalAnalyst disburses
    @PostMapping("/{id}/disburse")
    public ResponseEntity<String> disburseLoan(@PathVariable int id,
                                               Authentication authentication) {
        FinancialAuthDetails details = (FinancialAuthDetails) authentication.getDetails();
        User analyst = userPort.findByUserId(details.getUserId());
        analystUseCase.disburseLoan(id, analyst);
        return ResponseEntity.ok("Préstamo desembolsado correctamente");
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanResponse> findById(@PathVariable int id) {
        return ResponseEntity.ok(toLoanResponse(analystUseCase.findLoanById(id)));
    }

    @GetMapping
    public ResponseEntity<List<LoanResponse>> findAll() {
        List<LoanResponse> loans = analystUseCase.findAllLoans()
                .stream().map(LoanController::toLoanResponse).toList();
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<LoanResponse>> findByClient(@PathVariable Long clientId) {
        List<LoanResponse> loans = analystUseCase.findLoansByClientId(clientId)
                .stream().map(LoanController::toLoanResponse).toList();
        return ResponseEntity.ok(loans);
    }

    // ── Mappers ────────────────────────────────────────────────────────────────

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

    static LoanResponse toLoanResponse(BankLoan l) {
        Long clientId = l.getClient() != null ? l.getClient().getClientId() : null;
        String clientName = l.getClient() != null ? l.getClient().getFullName() : null;
        return new LoanResponse(l.getBankLoanId(), l.getBankLoanType(), clientId, clientName,
                l.getRequestedAmount(), l.getApprovedAmount(), l.getInterestRate(),
                l.getTermMonths(), l.getLoanState(), l.getApprovedDate(),
                l.getDisbursementDate(), l.getDestinationAccount() == 0 ? null : l.getDestinationAccount());
    }
}
