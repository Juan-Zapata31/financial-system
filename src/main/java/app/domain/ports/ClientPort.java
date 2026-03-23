package app.domain.ports;

import java.util.List;

public interface ClientPort {

    public ClientPort saveClient(ClientPort client);
    public ClientPort getClientById(String userId);
    public List<ClientPort> getAllClients();
    public void deleteClient(String userId);
    public ClientPort updateClient(ClientPort client);
}