package app.domain.ports;

import app.domain.models.User;
public interface UserPort {

    public boolean existsByDocument(String cedula);
    public void save(User user);
    public User findByDocument(User user);
    
}
