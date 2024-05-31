package br.com.ifsp.tickets.app.company.update;

import br.com.ifsp.tickets.domain.company.Company;
import br.com.ifsp.tickets.domain.company.CompanyID;
import br.com.ifsp.tickets.domain.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.company.vo.CNPJ;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalResourceAccessException;
import br.com.ifsp.tickets.domain.shared.exceptions.NoCompanyException;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.shared.validation.handler.Notification;
import br.com.ifsp.tickets.domain.user.User;

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
        if (!user.canManageCompany() && !user.canManageAnyCompany())
            throw new IllegalResourceAccessException("User does not have permission to update a company");
        if (!user.canManageAnyCompany() && !user.hasCompany()) throw new NoCompanyException();
        if (!user.canManageAnyCompany() && !user.getCompanyID().equals(companyID))
            throw new IllegalResourceAccessException("User does not have permission to update a company for another user");

        final Company company = this.companyGateway.findById(companyID).orElseThrow(() -> NotFoundException.with(Company.class, companyID));
        company.updateCompanyInfo(name, description, cnpj);
        final Notification notification = Notification.create();
        company.validate(notification);
        notification.throwPossibleErrors();
        return UpdateCompanyOutput.from(this.companyGateway.update(company));
    }
}
