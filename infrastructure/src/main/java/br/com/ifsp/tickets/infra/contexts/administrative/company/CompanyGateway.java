package br.com.ifsp.tickets.infra.contexts.administrative.company;

import br.com.ifsp.tickets.domain.administrative.company.Company;
import br.com.ifsp.tickets.domain.administrative.company.CompanyID;
import br.com.ifsp.tickets.domain.administrative.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.administrative.company.vo.CNPJ;
import br.com.ifsp.tickets.domain.shared.search.AdvancedSearchQuery;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.infra.contexts.administrative.company.persistence.CompanyJpaEntity;
import br.com.ifsp.tickets.infra.contexts.administrative.company.persistence.CompanyRepository;
import br.com.ifsp.tickets.infra.contexts.administrative.company.persistence.spec.CompanySpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
    public Pagination<Company> findAll(AdvancedSearchQuery sq) {
        final CompanySpecificationBuilder specificationBuilder = new CompanySpecificationBuilder();
        sq.filters().forEach(specificationBuilder::with);
        final Specification<CompanyJpaEntity> specification = specificationBuilder.build();
        final Sort orders = sq.sorts().stream().map(sort -> Sort.by(Sort.Direction.fromString(sort.direction()), sort.sort())).reduce(Sort::and).orElse(Sort.by(Sort.Order.asc("id")));
        final PageRequest request = PageRequest.of(
                sq.page(),
                sq.perPage(),
                orders
        );

        final Page<Company> page = this.companyRepository.findAll(specification, request).map(CompanyJpaEntity::toAggregate);

        return Pagination.of(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getContent()
        );
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
