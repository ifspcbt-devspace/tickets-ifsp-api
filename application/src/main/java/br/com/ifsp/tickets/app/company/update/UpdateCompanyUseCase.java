package br.com.ifsp.tickets.app.company.update;

import br.com.ifsp.tickets.domain.company.Company;
import br.com.ifsp.tickets.domain.company.CompanyID;
import br.com.ifsp.tickets.domain.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.company.vo.CNPJ;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalResourceAccessException;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.shared.validation.handler.Notification;
import br.com.ifsp.tickets.domain.user.User;
import br.com.ifsp.tickets.domain.user.vo.role.PermissionType;

public class UpdateCompanyUseCase implements IUpdateCompanyUseCase {

    private final ICompanyGateway companyGateway;

    public UpdateCompanyUseCase(ICompanyGateway companyGateway) {
        this.companyGateway = companyGateway;
    }

    @Override
    public UpdateCompanyOutput execute(UpdateCompanyInput anIn) {
        final CompanyID companyID = CompanyID.with(anIn.id());
        final CNPJ cnpj = new CNPJ(anIn.cnpj());
        final String name = anIn.name();
        final String description = anIn.description();
        final User user = anIn.authenticatedUser();
        final Company company = this.companyGateway.findById(companyID).orElseThrow(() -> NotFoundException.with(Company.class, companyID));

        if (!user.getRole().getPermissions().contains(PermissionType.MANAGE_COMPANIES) && !company.isOwner(user))
            throw new IllegalResourceAccessException("User does not have permission to update this company");

        company.updateCompanyInfo(name, description, cnpj);

        final Notification notification = Notification.create();
        company.validate(notification);
        notification.throwPossibleErrors();

        return UpdateCompanyOutput.from(this.companyGateway.update(company));
    }
}
