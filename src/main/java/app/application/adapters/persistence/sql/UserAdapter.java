package app.application.adapters.persistence.sql;

import app.application.adapters.persistence.sql.entities.UserEntity;
import app.application.adapters.persistence.sql.repositories.UserRepository;
import app.domain.models.User;
import app.domain.ports.UserPort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserAdapter implements UserPort {

    private final UserRepository userRepository;

    public UserAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        UserEntity saved = userRepository.save(toEntity(user));
        // Set the generated ID back on the domain object
        user.setUserId(saved.getUserId());
    }

    @Override
    public void update(User user) {
        UserEntity entity = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        entity.setUsername(user.getUsername());
        entity.setPassword(user.getPassword());
        entity.setRoles(user.getRoles());
        entity.setUserState(user.getUserState());
        entity.setCompanyNit(user.getCompanyNit());
        userRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User findByUserId(Long userId) {
        return userRepository.findById(userId).map(this::toDomain).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).map(this::toDomain).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByUsernameAndUserIdNot(String username, Long userId) {
        return userRepository.existsByUsernameAndUserIdNot(username, userId);
    }

    private UserEntity toEntity(User u) {
        UserEntity e = new UserEntity();
        e.setUserId(u.getUserId());
        e.setUsername(u.getUsername());
        e.setPassword(u.getPassword());
        e.setRoles(u.getRoles());
        e.setUserState(u.getUserState());
        e.setCompanyNit(u.getCompanyNit());
        return e;
    }

    private User toDomain(UserEntity e) {
        User u = new User();
        u.setUserId(e.getUserId());
        u.setUsername(e.getUsername());
        u.setPassword(e.getPassword());
        u.setRoles(e.getRoles());
        u.setUserState(e.getUserState());
        u.setCompanyNit(e.getCompanyNit());
        return u;
    }
}
