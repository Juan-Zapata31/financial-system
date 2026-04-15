package app.application.adapters.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import app.application.adapters.persistence.entities.BankLoanEntity;
import app.domain.models.User;
import app.domain.services.ApproveLoan;
import app.domain.services.DisburseLoan;
import app.domain.services.RejectLoan;
import app.domain.services.RequestLoan;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private RequestLoan requestLoan;

    @Autowired
    private ApproveLoan approveLoan;

    @Autowired
    private RejectLoan rejectLoan;

    @Autowired
    private DisburseLoan disburseLoan;

    // 🟢 Crear solicitud
    @PostMapping
    public BankLoanEntity requestLoan(@RequestBody BankLoanEntity loan) {
        return requestLoan.requestLoan(loan);
    }

    // 🔵 Aprobar préstamo
    @PostMapping("/{id}/approve")
    public BankLoanEntity approveLoan(@PathVariable String id, @RequestBody User user) {
        return approveLoan.approveLoan(id, user);
    }

    // 🔴 Rechazar préstamo
    @PostMapping("/{id}/reject")
    public BankLoanEntity rejectLoan(@PathVariable String id, @RequestBody User user) {
        return rejectLoan.rejectLoan(id, user);
    }

    // 💰 Desembolso
    @PostMapping("/{id}/disburse")
    public String disburseLoan(@PathVariable String id) {
        disburseLoan.disburseLoan(id);
        return "Préstamo desembolsado correctamente";
    }
}