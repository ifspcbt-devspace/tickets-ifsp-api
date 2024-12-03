package br.com.ifsp.tickets.app.administrative.company;

import br.com.ifsp.tickets.app.administrative.company.create.CreateCompanyUseCase;
import br.com.ifsp.tickets.app.administrative.company.create.ICreateCompanyUseCase;
import br.com.ifsp.tickets.app.administrative.company.delete.DeleteCompanyUseCase;
import br.com.ifsp.tickets.app.administrative.company.delete.IDeleteCompanyUseCase;
import br.com.ifsp.tickets.app.administrative.company.retrieve.get.GetCompanyByIdUseCase;
import br.com.ifsp.tickets.app.administrative.company.retrieve.get.IGetCompanyByIdUseCase;
import br.com.ifsp.tickets.app.administrative.company.retrieve.search.ISearchCompanyUseCase;
import br.com.ifsp.tickets.app.administrative.company.retrieve.search.SearchCompanyUseCase;
import br.com.ifsp.tickets.app.administrative.company.update.IUpdateCompanyUseCase;
import br.com.ifsp.tickets.app.administrative.company.update.UpdateCompanyUseCase;
import br.com.ifsp.tickets.domain.administrative.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.shared.IDomainEventPublisher;
import br.com.ifsp.tickets.domain.administrative.user.IUserGateway;

public class CompanyServiceFactory {

    private static CompanyService companyService;

    public static CompanyService create(ICompanyGateway companyGateway, IUserGateway userGateway, IDomainEventPublisher eventPublisher) {
        if (companyService == null) {
            final ICreateCompanyUseCase companyUseCase = new CreateCompanyUseCase(userGateway, companyGateway, eventPublisher);
            final IGetCompanyByIdUseCase getCompanyByIdUseCase = new GetCompanyByIdUseCase(companyGateway);
            final IDeleteCompanyUseCase deleteCompanyUseCase = new DeleteCompanyUseCase(companyGateway, eventPublisher);
            final IUpdateCompanyUseCase updateCompanyUseCase = new UpdateCompanyUseCase(companyGateway);
            final ISearchCompanyUseCase searchCompanyUseCase = new SearchCompanyUseCase(companyGateway);

            companyService = new CompanyService(
                    companyUseCase,
                    updateCompanyUseCase,
                    deleteCompanyUseCase,
                    getCompanyByIdUseCase,
                    searchCompanyUseCase
            );
        }
        return companyService;
    }

}
