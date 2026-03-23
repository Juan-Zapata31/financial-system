package app.domain.ports;

import java.util.List;

import app.domain.models.User;
public interface UserPort {

    public boolean existsByDocument(String cedula);
    public User findByDocument(User user);
    public void save(User user);
    public void delete(User user);
    public List<User> getAllUsers();
}
