package app.application.adapters.api.controllers;

import app.application.adapters.api.response.LoanResponse;
import app.application.usecases.AnalystUseCase;
import app.domain.models.TransactionLog;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/analyst")
public class AnalystController {

    private final AnalystUseCase analystUseCase;

    public AnalystController(AnalystUseCase analystUseCase) {
        this.analystUseCase = analystUseCase;
    }

    @GetMapping("/loans")
    public ResponseEntity<List<LoanResponse>> findAllLoans() {
        List<LoanResponse> loans = analystUseCase.findAllLoans()
                .stream().map(LoanController::toLoanResponse).toList();
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/loans/{id}")
    public ResponseEntity<LoanResponse> findLoan(@PathVariable int id) {
        return ResponseEntity.ok(LoanController.toLoanResponse(analystUseCase.findLoanById(id)));
    }

    @GetMapping("/logs")
    public ResponseEntity<List<TransactionLog>> findAllLogs() {
        return ResponseEntity.ok(analystUseCase.findAllLogs());
    }

    @GetMapping("/logs/{productId}")
    public ResponseEntity<List<TransactionLog>> findLogsByProduct(@PathVariable String productId) {
        return ResponseEntity.ok(analystUseCase.findLogsByProduct(productId));
    }
}
