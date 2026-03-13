package app.domain.models;

import app.domain.models.enums.Roles;

public class Employee extends User {
    private Roles typeEmployee;

    public Employee() {
    }

    public Employee(int idUser, int idRegister, String fullName, int idIdentityCard, String email, int phoneNumber, String address, String bornDate, String role, Boolean isActive) {
        super(idUser, idRegister, fullName, idIdentityCard, email, phoneNumber, address, bornDate, role, isActive);
    }

    public Roles getTypeEmployee() {
        return typeEmployee;
    }

    public void setTypeEmployee(Roles typeEmployee) {
        this.typeEmployee = typeEmployee;
    }
}
