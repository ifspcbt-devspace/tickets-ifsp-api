package br.com.ifsp.tickets.app.company.retrieve.get;

import br.com.ifsp.tickets.domain.company.Company;
import br.com.ifsp.tickets.domain.company.CompanyID;
import br.com.ifsp.tickets.domain.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;

public class GetCompanyByIdUseCase implements IGetCompanyByIdUseCase {

    private final ICompanyGateway companyGateway;

    public GetCompanyByIdUseCase(ICompanyGateway companyGateway) {
        this.companyGateway = companyGateway;
    }

    @Override
    public CompanyOutput execute(String anIn) {
        final CompanyID companyID = CompanyID.with(anIn);
        final Company company = this.companyGateway.findById(companyID).orElseThrow(() -> NotFoundException.with(Company.class, companyID));
        return CompanyOutput.from(company);
    }
}
