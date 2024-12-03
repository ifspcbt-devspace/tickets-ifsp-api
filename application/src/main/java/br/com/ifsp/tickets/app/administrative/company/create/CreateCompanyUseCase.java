package br.com.ifsp.tickets.app.administrative.company.create;

import br.com.ifsp.tickets.domain.administrative.company.Company;
import br.com.ifsp.tickets.domain.administrative.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.administrative.company.vo.CNPJ;
import br.com.ifsp.tickets.domain.shared.IDomainEventPublisher;
import br.com.ifsp.tickets.domain.shared.event.CompanyCreated;
import br.com.ifsp.tickets.domain.shared.exceptions.AlreadyJoinedACompany;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalResourceAccessException;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.shared.validation.handler.Notification;
import br.com.ifsp.tickets.domain.shared.vo.Address;
import br.com.ifsp.tickets.domain.administrative.user.IUserGateway;
import br.com.ifsp.tickets.domain.administrative.user.User;
import br.com.ifsp.tickets.domain.administrative.user.UserID;

public class CreateCompanyUseCase implements ICreateCompanyUseCase {

    private final IUserGateway userGateway;
    private final ICompanyGateway companyGateway;
    private final IDomainEventPublisher eventPublisher;

    public CreateCompanyUseCase(IUserGateway userGateway, ICompanyGateway companyGateway, IDomainEventPublisher eventPublisher) {
        this.userGateway = userGateway;
        this.companyGateway = companyGateway;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public CreateCompanyOutput execute(CreateCompanyInput anIn) {
        final UserID ownerID = UserID.with(anIn.ownerId());
        final User creator = anIn.user();
        final User owner = ownerID.equals(creator.getId()) ? creator : this.userGateway.findById(ownerID).orElseThrow(() -> NotFoundException.with(User.class, ownerID));
        if (owner.hasCompany()) throw new AlreadyJoinedACompany();
        if (!creator.canManageCompany() && !creator.canManageAnyCompany())
            throw new IllegalResourceAccessException("User does not have permission to create a company");
        if (!creator.canManageAnyCompany() && !creator.getId().equals(ownerID))
            throw new IllegalResourceAccessException("User does not have permission to create a company for another user");
        if (!owner.canManageCompany() && !owner.canManageAnyCompany())
            throw new IllegalResourceAccessException("Target user does not have permission to owner a company");

        final String name = anIn.name();
        final String description = anIn.description();
        final CNPJ cnpj = new CNPJ(anIn.cnpj());
        final String street = anIn.street();
        final String complement = anIn.complement();
        final String number = anIn.number();
        final String neighborhood = anIn.neighborhood();
        final String city = anIn.city();
        final String state = anIn.state();
        final String zipCode = anIn.zipCode();

        final Address address = Address.with(street, complement, number, neighborhood, city, state, "Brasil", zipCode);

        Company company = Company.newCompany(
                name,
                description,
                cnpj,
                ownerID,
                address
        );

        final Notification notification = Notification.create("An error occurred while validating the company");
        company.validate(notification);
        notification.throwAnyErrors();
        company = this.companyGateway.create(company);
        owner.joinCompany(company.getId());
        this.userGateway.update(owner);

        company.registerEvent(new CompanyCreated(company, creator));
        company.publishDomainEvents(this.eventPublisher);

        return CreateCompanyOutput.from(company);
    }
}
