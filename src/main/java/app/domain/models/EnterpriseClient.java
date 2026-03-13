package app.domain.models;

public class EnterpriseClient extends User {
    String companyName;
    int nit;
    String legalRepresentative;

    public EnterpriseClient() {
    }

    public EnterpriseClient(int idUser, int idRegister, String fullName, int idIdentityCard, String email, int phoneNumber, String address, String bornDate, String role, Boolean isActive, String companyName, int nit, String legalRepresentative) {
        super(idUser, idRegister, fullName, idIdentityCard, email, phoneNumber, address, bornDate, role, isActive);
        this.companyName = companyName;
        this.nit = nit;
        this.legalRepresentative = legalRepresentative;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getNit() {
        return nit;
    }

    public void setNit(int nit) {
        this.nit = nit;
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
    }
}
