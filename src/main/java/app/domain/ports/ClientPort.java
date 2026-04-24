package app.domain.ports;

import app.domain.models.Client;
import java.util.List;

public interface ClientPort {
    Client saveClient(Client client);
    Client getClientById(Long clientId);
    Client getClientByDocumentNumber(String documentNumber);
    List<Client> getAllClients();
    void deleteClient(Long clientId);
    Client updateClient(Client client);
    boolean existsByDocumentNumber(String documentNumber);
}
