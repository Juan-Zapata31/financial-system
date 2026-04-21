package app.domain.ports;

import app.domain.models.User;
import java.util.List;

public interface UserPort {
    void save(User user);
    void update(User user);
    void deleteByUserId(Long userId);
    User findByUserId(Long userId);
    User findByUsername(String username);
    List<User> getAllUsers();
    boolean existsByUsername(String username);
    boolean existsByUsernameAndUserIdNot(String username, Long userId);
}
