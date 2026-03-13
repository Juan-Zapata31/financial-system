package app.domain.models;

public abstract class User {
    private int idUser;
    private int idRegister;
    private String fullName;
    private int idIdentityCard;
    private String email;
    private int phoneNumber;
    private String address;
    private String bornDate;
    private String Role;
    private Boolean isActive;


    public User() {
    }

    public User(int idUser, int idRegister, String fullName, int idIdentityCard, String email, int phoneNumber, String address, String bornDate, String role, Boolean isActive) {
        this.idUser = idUser;
        this.idRegister = idRegister;
        this.fullName = fullName;
        this.idIdentityCard = idIdentityCard;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.bornDate = bornDate;
        Role = role;
        this.isActive = isActive;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdRegister() {
        return idRegister;
    }  

    public void setIdRegister(int idRegister) {
        this.idRegister = idRegister;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getIdIdentityCard() {
        return idIdentityCard;
    }

    public void setIdIdentityCard(int idIdentityCard) {
        this.idIdentityCard = idIdentityCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBornDate() {
        return bornDate;
    }

    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {       
        return "idUser: " + idUser + ", idRegister: " + idRegister + ", fullName: " + fullName + ", idIdentityCard: " + idIdentityCard + ", email: " + email + ", phoneNumber: " + phoneNumber + ", address: " + address + ", bornDate: " + bornDate + ", Role: " + Role + ", isActive: " + isActive;    
    }
}
