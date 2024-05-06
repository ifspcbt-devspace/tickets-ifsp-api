package br.com.ifsp.tickets.app.company;

import br.com.ifsp.tickets.app.company.create.CreateCompanyInput;
import br.com.ifsp.tickets.app.company.create.CreateCompanyOutput;
import br.com.ifsp.tickets.app.company.create.ICreateCompanyUseCase;
import br.com.ifsp.tickets.app.company.delete.DeleteCompanyInput;
import br.com.ifsp.tickets.app.company.delete.IDeleteCompanyUseCase;
import br.com.ifsp.tickets.app.company.get.CompanyOutput;
import br.com.ifsp.tickets.app.company.get.IGetCompanyByIdUseCase;
import br.com.ifsp.tickets.app.company.update.IUpdateCompanyUseCase;
import br.com.ifsp.tickets.app.company.update.UpdateCompanyInput;
import br.com.ifsp.tickets.app.company.update.UpdateCompanyOutput;

public class CompanyService {

    private final ICreateCompanyUseCase createCompanyUseCase;
    private final IUpdateCompanyUseCase updateCompanyUseCase;
    private final IDeleteCompanyUseCase deleteCompanyUseCase;
    private final IGetCompanyByIdUseCase getCompanyByIdUseCase;

    public CompanyService(ICreateCompanyUseCase createCompanyUseCase, IUpdateCompanyUseCase updateCompanyUseCase, IDeleteCompanyUseCase deleteCompanyUseCase, IGetCompanyByIdUseCase getCompanyByIdUseCase) {
        this.createCompanyUseCase = createCompanyUseCase;
        this.updateCompanyUseCase = updateCompanyUseCase;
        this.deleteCompanyUseCase = deleteCompanyUseCase;
        this.getCompanyByIdUseCase = getCompanyByIdUseCase;
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

    public CompanyOutput getCompanyById(String id) {
        return this.getCompanyByIdUseCase.execute(id);
    }
}
