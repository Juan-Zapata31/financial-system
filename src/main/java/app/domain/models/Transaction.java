package app.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction{
    private int idTrasaction;
    private int accountOrigin;
    private int accountDestination;
    private double amount;
    private String typeCoin;
    private boolean statusTrasaction;
    private String typeTransaction;
    private String descriptionTransaction;
}