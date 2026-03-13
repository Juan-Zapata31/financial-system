package app.domain.models;

import app.domain.models.enums.Roles;

public class NaturalClient extends User {
    private Roles typeCostumer;


    public NaturalClient() {
    }

    public NaturalClient(int idUser, int idRegister, String fullName, int idIdentityCard, String email, int phoneNumber, String address, String bornDate, String role, Boolean isActive, String idCostumer, String password) {
        super(idUser, idRegister, fullName, idIdentityCard, email, phoneNumber, address, bornDate, role, isActive);
    }

    public Roles getTypeCostumer() {
        return typeCostumer;
    }

    public void setTypeCostumer(Roles typeCostumer) {
        this.typeCostumer = typeCostumer;
    }

}