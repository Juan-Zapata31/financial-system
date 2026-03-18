package app.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BankLoan {
    private int idBankLoan;
    private String typeBankLoan;
    private int idApplicant;
    private double amountApplicant;
    private double amountApproved;
    private double interestRate;
    private int termMonths;
    private boolean statusLoan;
    private String dateApproved;
    private String dateDisbursement;
    private int idAccountDestination;
}
