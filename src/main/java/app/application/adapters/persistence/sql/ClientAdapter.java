package app.application.adapters.persistence.sql;

import app.application.adapters.persistence.sql.entities.ClientEntity;
import app.application.adapters.persistence.sql.entities.EnterpriseClientEntity;
import app.application.adapters.persistence.sql.entities.NaturalClientEntity;
import app.application.adapters.persistence.sql.repositories.ClientRepository;
import app.application.adapters.persistence.sql.repositories.UserRepository;
import app.domain.models.Client;
import app.domain.models.EnterpriseClient;
import app.domain.models.NaturalClient;
import app.domain.models.User;
import app.domain.ports.ClientPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ClientAdapter implements ClientPort {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    public ClientAdapter(ClientRepository clientRepository, UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Client saveClient(Client client) {
        ClientEntity saved = clientRepository.save(toEntity(client));
        return toDomain(saved);
    }

    @Override
    public Client getClientById(Long clientId) {
        return clientRepository.findById(clientId).map(this::toDomain).orElse(null);
    }

    @Override
    public Client getClientByDocumentNumber(String documentNumber) {
        return clientRepository.findByDocumentNumber(documentNumber).map(this::toDomain).orElse(null);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteClient(Long clientId) {
        clientRepository.deleteById(clientId);
    }

    @Override
    public Client updateClient(Client client) {
        ClientEntity entity = clientRepository.findById(client.getClientId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        entity.setFullName(client.getFullName());
        entity.setDocumentNumber(client.getDocumentNumber());
        entity.setEmail(client.getEmail());
        entity.setPhone(client.getPhone());
        entity.setAddress(client.getAddress());
        if (entity instanceof NaturalClientEntity nat && client instanceof NaturalClient nc) {
            nat.setBirthDate(nc.getBirthDate());
        }
        if (entity instanceof EnterpriseClientEntity ent && client instanceof EnterpriseClient ec) {
            ent.setCompanyName(ec.getCompanyName());
            ent.setNit(ec.getNit());
            ent.setLegalRepresentative(ec.getLegalRepresentative());
        }
        return toDomain(clientRepository.save(entity));
    }

    @Override
    public boolean existsByDocumentNumber(String documentNumber) {
        return clientRepository.existsByDocumentNumber(documentNumber);
    }

    private ClientEntity toEntity(Client c) {
        ClientEntity e;
        if (c instanceof NaturalClient nc) {
            NaturalClientEntity nat = new NaturalClientEntity();
            nat.setBirthDate(nc.getBirthDate());
            e = nat;
        } else if (c instanceof EnterpriseClient ec) {
            EnterpriseClientEntity ent = new EnterpriseClientEntity();
            ent.setCompanyName(ec.getCompanyName());
            ent.setNit(ec.getNit());
            ent.setLegalRepresentative(ec.getLegalRepresentative());
            e = ent;
        } else {
            throw new RuntimeException("Tipo de cliente no soportado");
        }
        e.setClientId(c.getClientId());
        e.setFullName(c.getFullName());
        e.setDocumentNumber(c.getDocumentNumber());
        e.setEmail(c.getEmail());
        e.setPhone(c.getPhone());
        e.setAddress(c.getAddress());
        if (c.getUser() != null && c.getUser().getUserId() != null) {
            userRepository.findById(c.getUser().getUserId()).ifPresent(e::setUser);
        }
        return e;
    }

    Client toDomain(ClientEntity e) {
        Client c;
        if (e instanceof NaturalClientEntity nat) {
            NaturalClient nc = new NaturalClient();
            nc.setBirthDate(nat.getBirthDate());
            c = nc;
        } else if (e instanceof EnterpriseClientEntity ent) {
            EnterpriseClient ec = new EnterpriseClient();
            ec.setCompanyName(ent.getCompanyName());
            ec.setNit(ent.getNit());
            ec.setLegalRepresentative(ent.getLegalRepresentative());
            c = ec;
        } else {
            throw new RuntimeException("Tipo de entidad cliente no soportado");
        }
        c.setClientId(e.getClientId());
        c.setFullName(e.getFullName());
        c.setDocumentNumber(e.getDocumentNumber());
        c.setEmail(e.getEmail());
        c.setPhone(e.getPhone());
        c.setAddress(e.getAddress());
        if (e.getUser() != null) {
            User u = new User();
            u.setUserId(e.getUser().getUserId());
            u.setUsername(e.getUser().getUsername());
            u.setRoles(e.getUser().getRoles());
            u.setUserState(e.getUser().getUserState());
            c.setUser(u);
        }
        return c;
    }
}
