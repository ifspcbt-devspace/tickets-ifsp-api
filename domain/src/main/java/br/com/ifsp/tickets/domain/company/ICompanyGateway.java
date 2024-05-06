package br.com.ifsp.tickets.domain.company;

import br.com.ifsp.tickets.domain.company.vo.CNPJ;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.shared.search.SearchQuery;

import java.util.Optional;

public interface ICompanyGateway {

    Company create(Company company);

    Optional<Company> findById(CompanyID id);

    Optional<Company> findByCnpj(CNPJ cnpj);

    Pagination<Company> findAll(SearchQuery sq);

    Company update(Company company);

    void delete(Company company);

    boolean exists(CompanyID id);
}
