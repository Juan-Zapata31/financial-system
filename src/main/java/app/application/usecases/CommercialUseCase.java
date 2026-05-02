package app.application.usecases;

import app.domain.exceptions.NotFoundException;
import app.domain.models.BankLoan;
import app.domain.models.Client;
import app.domain.models.EnterpriseClient;
import app.domain.models.NaturalClient;
import app.domain.models.User;
import app.domain.ports.ClientPort;
import app.domain.services.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommercialUseCase {

    private final RequestLoan requestLoan;
    private final FindLoan findLoan;
    private final CreateClient createClient;
    private final ClientPort clientPort;

    public CommercialUseCase(RequestLoan requestLoan, FindLoan findLoan,
            CreateClient createClient,
            ClientPort clientPort) {
        this.requestLoan = requestLoan;
        this.findLoan = findLoan;
        this.createClient = createClient;
        this.clientPort = clientPort;
    }

    // ── Clients ───────────────────────────────────────────────────────────────

    public Client createNaturalClient(NaturalClient client, Long requestingUserId,
            String requestingUsername) {
        return createClient.createNaturalClient(client, requestingUserId, requestingUsername);
    }

    public Client createEnterpriseClient(EnterpriseClient client, Long requestingUserId,
            String requestingUsername) {
        return createClient.createEnterpriseClient(client, requestingUserId, requestingUsername);
    }

    public Client findClientById(Long clientId) {
        Client client = clientPort.getClientById(clientId);
        if (client == null) {
            throw new NotFoundException("No existe un cliente con ID: " + clientId);
        }
        return client;
    }

    public Client findClientByDocument(String documentNumber) {
        Client client = clientPort.getClientByDocumentNumber(documentNumber);
        if (client == null) {
            throw new NotFoundException("No existe un cliente con documento: " + documentNumber);
        }
        return client;
    }

    public List<Client> findAllClients() {
        return clientPort.getAllClients();
    }

    // ── Loans ─────────────────────────────────────────────────────────────────

    public BankLoan requestLoan(BankLoan loan, User commercial) {
        return requestLoan.requestLoan(loan, commercial.getUserId(), commercial.getUsername());
    }

    public BankLoan findLoanById(int id) {
        return findLoan.findById(id);
    }

    public List<BankLoan> findLoansByClientId(Long clientId) {
        return findLoan.findByClientId(clientId);
    }
}
