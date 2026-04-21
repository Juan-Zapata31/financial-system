package app.infrastructure.security;

import lombok.Getter;

@Getter
public class FinancialAuthDetails {
    private final Long userId;
    private final String username;
    private final String role;
    private final String companyNit;

    public FinancialAuthDetails(Long userId, String username, String role, String companyNit) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.companyNit = companyNit;
    }
}
