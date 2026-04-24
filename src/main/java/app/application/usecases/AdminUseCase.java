package app.application.usecases;

import app.domain.models.User;
import app.domain.models.enums.UserState;
import app.domain.services.CreateUser;
import app.domain.services.FindUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUseCase {

    private final CreateUser createUser;
    private final FindUser findUser;
    private final app.domain.ports.UserPort userPort;
    private final PasswordEncoder passwordEncoder;

    public AdminUseCase(CreateUser createUser, FindUser findUser,
                        app.domain.ports.UserPort userPort,
                        PasswordEncoder passwordEncoder) {
        this.createUser = createUser;
        this.findUser = findUser;
        this.userPort = userPort;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getUserState() == null) user.setUserState(UserState.ACTIVE);
        createUser.createUser(user);
    }

    public void updateUser(User user) {
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userPort.update(user);
    }

    public void deleteUser(Long userId) { userPort.deleteByUserId(userId); }
    public User findUser(Long userId) { return findUser.findByUserId(userId); }
    public List<User> findAllUsers() { return findUser.findAll(); }
}
