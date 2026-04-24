package app.domain.services;

import app.domain.exceptions.NotFoundException;
import app.domain.models.User;
import app.domain.ports.UserPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindUser {

    private final UserPort userPort;

    public FindUser(UserPort userPort) {
        this.userPort = userPort;
    }

    public User findByUserId(Long userId) {
        User user = userPort.findByUserId(userId);
        if (user == null) throw new NotFoundException("No existe un usuario con ese ID");
        return user;
    }

    public User findByUsername(String username) {
        User user = userPort.findByUsername(username);
        if (user == null) throw new NotFoundException("No existe un usuario con ese username");
        return user;
    }

    public List<User> findAll() {
        return userPort.getAllUsers();
    }
}
