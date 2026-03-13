package app.domain.models;

public class Account {
    private int idAccount;
    private String typeAccount;
    private String currentAmount;
    private String coin;
    private boolean statusAccount;
    private String creationDate;

    public Account() {
    }

    public Account(int idAccount, String typeAccount, String currentAmount, String coin, boolean statusAccount, String creationDate) {
        this.idAccount = idAccount;
        this.typeAccount = typeAccount;
        this.currentAmount = currentAmount;
        this.coin = coin;
        this.statusAccount = statusAccount;
        this.creationDate = creationDate;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public String getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(String typeAccount) {
        this.typeAccount = typeAccount;
    }

    public String getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(String currentAmount) {
        this.currentAmount = currentAmount;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public boolean isStatusAccount() {
        return statusAccount;
    }

    public void setStatusAccount(boolean statusAccount) {
        this.statusAccount = statusAccount;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
