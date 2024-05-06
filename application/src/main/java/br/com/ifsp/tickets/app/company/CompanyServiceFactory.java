package br.com.ifsp.tickets.app.company;

import br.com.ifsp.tickets.app.company.create.CreateCompanyUseCase;
import br.com.ifsp.tickets.app.company.create.ICreateCompanyUseCase;
import br.com.ifsp.tickets.app.company.delete.DeleteCompanyUseCase;
import br.com.ifsp.tickets.app.company.delete.IDeleteCompanyUseCase;
import br.com.ifsp.tickets.app.company.get.GetCompanyByIdUseCase;
import br.com.ifsp.tickets.app.company.get.IGetCompanyByIdUseCase;
import br.com.ifsp.tickets.app.company.update.IUpdateCompanyUseCase;
import br.com.ifsp.tickets.app.company.update.UpdateCompanyUseCase;
import br.com.ifsp.tickets.domain.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.user.IUserGateway;

public class CompanyServiceFactory {

    private static CompanyService companyService;

    public static CompanyService create(ICompanyGateway companyGateway, IUserGateway userGateway) {
        if (companyService == null) {
            final ICreateCompanyUseCase companyUseCase = new CreateCompanyUseCase(userGateway, companyGateway);
            final IGetCompanyByIdUseCase getCompanyByIdUseCase = new GetCompanyByIdUseCase(companyGateway);
            final IDeleteCompanyUseCase deleteCompanyUseCase = new DeleteCompanyUseCase(companyGateway);
            final IUpdateCompanyUseCase updateCompanyUseCase = new UpdateCompanyUseCase(companyGateway);

            companyService = new CompanyService(
                    companyUseCase,
                    updateCompanyUseCase,
                    deleteCompanyUseCase,
                    getCompanyByIdUseCase
            );
        }
        return companyService;
    }

}
