package br.com.ifsp.tickets.app.company.create;

import br.com.ifsp.tickets.domain.company.Company;
import br.com.ifsp.tickets.domain.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.company.vo.CNPJ;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalResourceAccess;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.shared.validation.handler.Notification;
import br.com.ifsp.tickets.domain.shared.vo.Address;
import br.com.ifsp.tickets.domain.user.IUserGateway;
import br.com.ifsp.tickets.domain.user.User;
import br.com.ifsp.tickets.domain.user.UserID;
import br.com.ifsp.tickets.domain.user.vo.role.PermissionType;

public class CreateCompanyUseCase implements ICreateCompanyUseCase {

    private final IUserGateway userGateway;
    private final ICompanyGateway companyGateway;

    public CreateCompanyUseCase(IUserGateway userGateway, ICompanyGateway companyGateway) {
        this.userGateway = userGateway;
        this.companyGateway = companyGateway;
    }

    @Override
    public CreateCompanyOutput execute(CreateCompanyInput anIn) {
        final UserID userID = UserID.with(anIn.ownerId());
        final User user = this.userGateway.findById(userID).orElseThrow(() -> NotFoundException.with(User.class, userID));

        if (!user.getRole().getPermissions().contains(PermissionType.MANAGE_A_COMPANY))
            throw new IllegalResourceAccess();

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

        final Company company = Company.newCompany(
                name,
                description,
                cnpj,
                userID,
                address
        );

        final Notification notification = Notification.create();
        company.validate(notification);
        notification.throwPossibleErrors();

        return CreateCompanyOutput.from(this.companyGateway.create(company));
    }
}
