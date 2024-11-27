package br.com.ifsp.tickets.app.company.delete;

import br.com.ifsp.tickets.domain.company.Company;
import br.com.ifsp.tickets.domain.company.CompanyID;
import br.com.ifsp.tickets.domain.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.shared.IDomainEventPublisher;
import br.com.ifsp.tickets.domain.shared.event.CompanyDeleted;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalResourceAccessException;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.user.User;

public class DeleteCompanyUseCase implements IDeleteCompanyUseCase {

    private final ICompanyGateway companyGateway;
    private final IDomainEventPublisher eventPublisher;

    public DeleteCompanyUseCase(ICompanyGateway companyGateway, IDomainEventPublisher eventPublisher) {
        this.companyGateway = companyGateway;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void execute(DeleteCompanyInput anIn) {
        final CompanyID companyID = CompanyID.with(anIn.id());
        final Company company = this.companyGateway.findById(companyID).orElseThrow(() -> NotFoundException.with(Company.class, companyID));
        final User authenticatedUser = anIn.authenticatedUser();
        if (!authenticatedUser.canManageAnyCompany() && !company.isOwner(authenticatedUser))
            throw new IllegalResourceAccessException("User does not have permission to delete this company");

        // TODO - lidar com os tickets, eventos e usuários associados a essa empresa
        this.companyGateway.delete(company);

        company.registerEvent(new CompanyDeleted(company, authenticatedUser));
        company.publishDomainEvents(this.eventPublisher);
    }
}
