package app.application.usecases;

import app.domain.exceptions.BusinessException;
import app.domain.models.User;
import app.domain.ports.UserPort;
import app.infrastructure.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthUseCase {

    private final UserPort userPort;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthUseCase(UserPort userPort, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userPort = userPort;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String login(String username, String password) {
        User user = userPort.findByUsername(username);
        if (user == null) {
            throw new BusinessException("INVALID_CREDENTIALS", "Credenciales inválidas");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException("INVALID_CREDENTIALS", "Credenciales inválidas");
        }
        return jwtUtil.generateToken(
                user.getUserId(),
                user.getUsername(),
                user.getRoles().name(),
                user.getCompanyNit()
        );
    }
}
