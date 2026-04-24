package app.application.adapters.persistence.sql;

import app.application.adapters.persistence.sql.entities.BankLoanEntity;
import app.application.adapters.persistence.sql.repositories.ClientRepository;
import app.application.adapters.persistence.sql.repositories.LoanRepository;
import app.domain.models.BankLoan;
import app.domain.ports.LoanPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class LoanAdapter implements LoanPort {

    private final LoanRepository loanRepository;
    private final ClientRepository clientRepository;
    private final ClientAdapter clientAdapter;

    public LoanAdapter(LoanRepository loanRepository,
                       ClientRepository clientRepository,
                       ClientAdapter clientAdapter) {
        this.loanRepository = loanRepository;
        this.clientRepository = clientRepository;
        this.clientAdapter = clientAdapter;
    }

    @Override
    public BankLoan save(BankLoan loan) {
        BankLoanEntity saved = loanRepository.save(toEntity(loan));
        loan.setBankLoanId(saved.getBankLoanId());
        return toDomain(saved);
    }

    @Override
    public BankLoan findById(int loanId) {
        return loanRepository.findById(loanId).map(this::toDomain).orElse(null);
    }

    @Override
    public List<BankLoan> findByClientId(Long clientId) {
        return loanRepository.findByClient_ClientId(clientId)
                .stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<BankLoan> findAll() {
        return loanRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    private BankLoanEntity toEntity(BankLoan l) {
        BankLoanEntity e = new BankLoanEntity();
        e.setBankLoanId(l.getBankLoanId() == 0 ? null : l.getBankLoanId());
        e.setBankLoanType(l.getBankLoanType());
        e.setRequestedAmount(l.getRequestedAmount());
        e.setApprovedAmount(l.getApprovedAmount());
        e.setInterestRate(l.getInterestRate());
        e.setTermMonths(l.getTermMonths());
        e.setLoanState(l.getLoanState());
        e.setApprovedDate(l.getApprovedDate());
        e.setDisbursementDate(l.getDisbursementDate());
        e.setDestinationAccount(l.getDestinationAccount() == 0 ? null : l.getDestinationAccount());
        e.setAnalystId(l.getAnalystId());
        if (l.getClient() != null && l.getClient().getClientId() != null) {
            clientRepository.findById(l.getClient().getClientId()).ifPresent(e::setClient);
        }
        return e;
    }

    private BankLoan toDomain(BankLoanEntity e) {
        BankLoan l = new BankLoan();
        l.setBankLoanId(e.getBankLoanId());
        l.setBankLoanType(e.getBankLoanType());
        l.setRequestedAmount(e.getRequestedAmount());
        l.setApprovedAmount(e.getApprovedAmount());
        l.setInterestRate(e.getInterestRate());
        l.setTermMonths(e.getTermMonths());
        l.setLoanState(e.getLoanState());
        l.setApprovedDate(e.getApprovedDate());
        l.setDisbursementDate(e.getDisbursementDate());
        l.setDestinationAccount(e.getDestinationAccount() != null ? e.getDestinationAccount() : 0);
        l.setAnalystId(e.getAnalystId());
        if (e.getClient() != null) {
            l.setClient(clientAdapter.toDomain(e.getClient()));
        }
        return l;
    }
}
