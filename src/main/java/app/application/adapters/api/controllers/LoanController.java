package app.application.adapters.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import app.domain.models.BankLoan;
import app.domain.models.User;
import app.domain.services.LoanService;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    // 🟢 Crear solicitud
    @PostMapping
    public BankLoan requestLoan(@RequestBody BankLoan loan) {
        return loanService.requestLoan(loan);
    }

    // 🔵 Aprobar préstamo
    @PostMapping("/{id}/approve")
    public BankLoan approveLoan(@PathVariable String id, @RequestBody User user) {
        return loanService.approveLoan(id, user);
    }

    // 🔴 Rechazar préstamo
    @PostMapping("/{id}/reject")
    public BankLoan rejectLoan(@PathVariable String id, @RequestBody User user) {
        return loanService.rejectLoan(id, user);
    }

    // 💰 Desembolso
    @PostMapping("/{id}/disburse")
    public String disburseLoan(@PathVariable String id) {
        loanService.disburseLoan(id);
        return "Préstamo desembolsado correctamente";
    }
}