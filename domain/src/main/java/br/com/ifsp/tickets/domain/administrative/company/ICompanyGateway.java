package br.com.ifsp.tickets.domain.administrative.company;

import br.com.ifsp.tickets.domain.administrative.company.vo.CNPJ;
import br.com.ifsp.tickets.domain.shared.search.AdvancedSearchQuery;
import br.com.ifsp.tickets.domain.shared.search.Pagination;

import java.util.Optional;

public interface ICompanyGateway {

    Company create(Company company);

    Optional<Company> findById(CompanyID id);

    Optional<Company> findByCnpj(CNPJ cnpj);

    Pagination<Company> findAll(AdvancedSearchQuery sq);

    Company update(Company company);

    void delete(Company company);

    boolean exists(CompanyID id);
}
