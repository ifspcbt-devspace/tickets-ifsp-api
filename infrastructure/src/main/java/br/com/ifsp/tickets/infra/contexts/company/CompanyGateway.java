package br.com.ifsp.tickets.infra.contexts.company;

import br.com.ifsp.tickets.domain.company.Company;
import br.com.ifsp.tickets.domain.company.CompanyID;
import br.com.ifsp.tickets.domain.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.company.vo.CNPJ;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.shared.search.SearchQuery;
import br.com.ifsp.tickets.infra.contexts.company.persistence.CompanyJpaEntity;
import br.com.ifsp.tickets.infra.contexts.company.persistence.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class CompanyGateway implements ICompanyGateway {

    private final CompanyRepository companyRepository;

    @Override
    public Company create(Company company) {
        return this.companyRepository.save(CompanyJpaEntity.from(company)).toAggregate();
    }

    @Override
    public Optional<Company> findById(CompanyID id) {
        return this.companyRepository.findById(id.getValue()).map(CompanyJpaEntity::toAggregate);
    }

    @Override
    public Optional<Company> findByCnpj(CNPJ cnpj) {
        return this.companyRepository.findByCnpj(cnpj.getValue()).map(CompanyJpaEntity::toAggregate);
    }

    @Override
    public Pagination<Company> findAll(SearchQuery sq) {
        return null;
    }

    @Override
    public Company update(Company company) {
        return this.companyRepository.save(CompanyJpaEntity.from(company)).toAggregate();
    }

    @Override
    public void delete(Company company) {
        this.companyRepository.delete(CompanyJpaEntity.from(company));
    }

    @Override
    public boolean exists(CompanyID id) {
        return this.companyRepository.existsById(id.getValue());
    }
}
