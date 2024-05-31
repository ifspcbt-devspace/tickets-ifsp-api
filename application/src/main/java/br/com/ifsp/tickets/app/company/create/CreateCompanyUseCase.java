package br.com.ifsp.tickets.app.company.create;

import br.com.ifsp.tickets.domain.company.Company;
import br.com.ifsp.tickets.domain.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.company.vo.CNPJ;
import br.com.ifsp.tickets.domain.shared.exceptions.AlreadyJoinedACompany;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalResourceAccessException;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.shared.validation.handler.Notification;
import br.com.ifsp.tickets.domain.shared.vo.Address;
import br.com.ifsp.tickets.domain.user.IUserGateway;
import br.com.ifsp.tickets.domain.user.User;
import br.com.ifsp.tickets.domain.user.UserID;

public class CreateCompanyUseCase implements ICreateCompanyUseCase {

    private final IUserGateway userGateway;
    private final ICompanyGateway companyGateway;

    public CreateCompanyUseCase(IUserGateway userGateway, ICompanyGateway companyGateway) {
        this.userGateway = userGateway;
        this.companyGateway = companyGateway;
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

        final Notification notification = Notification.create();
        company.validate(notification);
        notification.throwPossibleErrors();
        company = this.companyGateway.create(company);
        owner.joinCompany(company.getId());
        this.userGateway.update(owner);
        return CreateCompanyOutput.from(company);
    }
}
