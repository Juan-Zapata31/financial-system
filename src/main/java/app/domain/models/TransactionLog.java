package app.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionLog {
    private int idRegister;
    private String typeOperation;
    private String responsableUser;
    private String descriptionOperation;
    private String resultOperation;
    private int idOrigin;
}
