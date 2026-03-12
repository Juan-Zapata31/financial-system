package app.domain.models;

import app.domain.models.enums.Roles;

public class Costumer extends User {
    String idCostumer;
    Roles typeCostumer;


    public Costumer() {
    }

    public Costumer(int idUser, String idRegister, String fullName, int idIdentityCard, String email, int phoneNumber, String address, String role, Boolean isActive, String idCostumer, String password) {
        super(idUser, idRegister, fullName, idIdentityCard, email, phoneNumber, address, role, isActive);
        this.idCostumer = idCostumer;
    }

    public String getIdCostumer() {
        return idCostumer;
    }

    public void setIdCostumer(String idCostumer) {
        this.idCostumer = idCostumer;
    }

    public Roles getTypeCostumer() {
        return typeCostumer;
    }

    public void setTypeCostumer(Roles typeCostumer) {
        this.typeCostumer = typeCostumer;
    }

}