package br.com.ifsp.tickets.app.administrative.company.retrieve.get;

import br.com.ifsp.tickets.domain.administrative.company.Company;
import br.com.ifsp.tickets.domain.administrative.company.CompanyID;
import br.com.ifsp.tickets.domain.administrative.company.ICompanyGateway;
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
