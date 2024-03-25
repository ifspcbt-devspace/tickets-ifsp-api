package br.com.ifsp.tickets.domain.company;

import br.com.ifsp.tickets.domain.company.vo.CNPJ;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.shared.search.SearchQuery;

public interface ICompanyGateway {

    Company create(Company company);

    Company findById(CompanyID id);

    Company findByCnpj(CNPJ cnpj);

    Pagination<Company> findAll(SearchQuery sq);

    Company update(Company company);

    void delete(Company id);

    boolean exists(CompanyID id);
}
