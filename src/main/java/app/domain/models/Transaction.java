package app.domain.models;

public class Transaction{
    private int idTrasaction;
    private int accountOrigin;
    private int accountDestination;
    private double amount;
    private String typeCoin;
    private boolean statusTrasaction;
    private String typeTransaction;
    private String descriptionTransaction;

    public Transaction() {
    }

    public Transaction(int idTrasaction, int accountOrigin, int accountDestination, double amount, String typeCoin, boolean statusTrasaction, String typeTransaction, String descriptionTransaction) {
        this.idTrasaction = idTrasaction;
        this.accountOrigin = accountOrigin;
        this.accountDestination = accountDestination;
        this.amount = amount;
        this.typeCoin = typeCoin;
        this.statusTrasaction = statusTrasaction;
        this.typeTransaction = typeTransaction;
        this.descriptionTransaction = descriptionTransaction;
    }

    public int getIdTrasaction() {
        return idTrasaction;
    }

    public void setIdTrasaction(int idTrasaction) {
        this.idTrasaction = idTrasaction;
    }

    public int getAccountOrigin() {
        return accountOrigin;
    }

    public void setAccountOrigin(int accountOrigin) {
        this.accountOrigin = accountOrigin;
    }

    public int getAccountDestination() {
        return accountDestination;
    }

    public void setAccountDestination(int accountDestination) {
        this.accountDestination = accountDestination;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTypeCoin() {
        return typeCoin;
    }

    public void setTypeCoin(String typeCoin) {
        this.typeCoin = typeCoin;
    }

    public boolean isStatusTrasaction() {
        return statusTrasaction;
    }

    public void setStatusTrasaction(boolean statusTrasaction) {
        this.statusTrasaction = statusTrasaction;
    }

    public String getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(String typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public String getDescriptionTransaction() {
        return descriptionTransaction;
    }

    public void setDescriptionTransaction(String descriptionTransaction) {
        this.descriptionTransaction = descriptionTransaction;
    }
}