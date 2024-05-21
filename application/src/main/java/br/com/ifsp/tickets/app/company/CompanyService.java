package br.com.ifsp.tickets.app.company;

import br.com.ifsp.tickets.app.company.create.CreateCompanyInput;
import br.com.ifsp.tickets.app.company.create.CreateCompanyOutput;
import br.com.ifsp.tickets.app.company.create.ICreateCompanyUseCase;
import br.com.ifsp.tickets.app.company.delete.DeleteCompanyInput;
import br.com.ifsp.tickets.app.company.delete.IDeleteCompanyUseCase;
import br.com.ifsp.tickets.app.company.retrieve.get.CompanyOutput;
import br.com.ifsp.tickets.app.company.retrieve.get.IGetCompanyByIdUseCase;
import br.com.ifsp.tickets.app.company.retrieve.search.ISearchCompanyUseCase;
import br.com.ifsp.tickets.app.company.retrieve.search.SearchCompanyOutput;
import br.com.ifsp.tickets.app.company.update.IUpdateCompanyUseCase;
import br.com.ifsp.tickets.app.company.update.UpdateCompanyInput;
import br.com.ifsp.tickets.app.company.update.UpdateCompanyOutput;
import br.com.ifsp.tickets.domain.shared.search.AdvancedSearchQuery;
import br.com.ifsp.tickets.domain.shared.search.Pagination;

public class CompanyService {

    private final ICreateCompanyUseCase createCompanyUseCase;
    private final IUpdateCompanyUseCase updateCompanyUseCase;
    private final IDeleteCompanyUseCase deleteCompanyUseCase;
    private final IGetCompanyByIdUseCase getCompanyByIdUseCase;
    private final ISearchCompanyUseCase searchCompanyUseCase;

    public CompanyService(ICreateCompanyUseCase createCompanyUseCase, IUpdateCompanyUseCase updateCompanyUseCase, IDeleteCompanyUseCase deleteCompanyUseCase, IGetCompanyByIdUseCase getCompanyByIdUseCase, ISearchCompanyUseCase searchCompanyUseCase) {
        this.createCompanyUseCase = createCompanyUseCase;
        this.updateCompanyUseCase = updateCompanyUseCase;
        this.deleteCompanyUseCase = deleteCompanyUseCase;
        this.getCompanyByIdUseCase = getCompanyByIdUseCase;
        this.searchCompanyUseCase = searchCompanyUseCase;
    }

    public CreateCompanyOutput create(CreateCompanyInput input) {
        return this.createCompanyUseCase.execute(input);
    }

    public UpdateCompanyOutput update(UpdateCompanyInput input) {
        return this.updateCompanyUseCase.execute(input);
    }

    public void delete(DeleteCompanyInput input) {
        this.deleteCompanyUseCase.execute(input);
    }

    public CompanyOutput getById(String id) {
        return this.getCompanyByIdUseCase.execute(id);
    }

    public Pagination<SearchCompanyOutput> search(AdvancedSearchQuery input) {
        return this.searchCompanyUseCase.execute(input);
    }
}
