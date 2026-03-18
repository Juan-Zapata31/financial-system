package app.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private int idAccount;
    private String typeAccount;
    private double currentAmount;
    private String coin;
    private boolean statusAccount;
    private String creationDate;
}
