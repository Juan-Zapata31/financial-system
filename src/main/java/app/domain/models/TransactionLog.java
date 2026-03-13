package app.domain.models;

public class TransactionLog {
    private int idRegister;
    private String typeOperation;
    private String responsableUser;
    private String descriptionOperation;
    private String resultOperation;
    private int idOrigin;


    public TransactionLog() {
    }

    public TransactionLog(int idRegister, String typeOperation, String responsableUser, String descriptionOperation, String resultOperation, int idOrigen) {
        this.idRegister = idRegister;
        this.typeOperation = typeOperation;
        this.responsableUser = responsableUser;
        this.descriptionOperation = descriptionOperation;
        this.resultOperation = resultOperation;
        this.idOrigin = idOrigen;
    }

    public int getIdRegister() {
        return idRegister;
    }

    public void setIdRegister(int idRegister) {
        this.idRegister = idRegister;
    }

    public String getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
    }

    public String getResponsableUser() {
        return responsableUser;
    }

    public void setResponsableUser(String responsableUser) {
        this.responsableUser = responsableUser;
    }

    public String getDescriptionOperation() {
        return descriptionOperation;
    }

    public void setDescriptionOperation(String descriptionOperation) {
        this.descriptionOperation = descriptionOperation;
    }

    public String getResultOperation() {
        return resultOperation;
    }

    public void setResultOperation(String resultOperation) {
        this.resultOperation = resultOperation;
    }

    public int getIdOrigin() {
        return idOrigin;
    }

    public void setIdOrigin(int idOrigen) {
        this.idOrigin = idOrigen;
    }

    @Override
    public String toString() {
        return "TransactionLog [idRegister=" + idRegister + ", typeOperation=" + typeOperation + ", responsableUser=" + responsableUser + ", descriptionOperation=" + descriptionOperation + ", resultOperation=" + resultOperation + ", idOrigin=" + idOrigin + "]";
    }

}
