package br.com.ifsp.tickets.infra.api.controllers;

import br.com.ifsp.tickets.app.company.CompanyService;
import br.com.ifsp.tickets.app.company.create.CreateCompanyInput;
import br.com.ifsp.tickets.app.company.create.CreateCompanyOutput;
import br.com.ifsp.tickets.app.company.delete.DeleteCompanyInput;
import br.com.ifsp.tickets.app.company.retrieve.get.CompanyOutput;
import br.com.ifsp.tickets.app.company.update.UpdateCompanyInput;
import br.com.ifsp.tickets.app.company.update.UpdateCompanyOutput;
import br.com.ifsp.tickets.domain.shared.search.AdvancedSearchQuery;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.infra.api.CompanyAPI;
import br.com.ifsp.tickets.infra.contexts.company.models.CompanyResponse;
import br.com.ifsp.tickets.infra.contexts.company.models.CreateCompanyRequest;
import br.com.ifsp.tickets.infra.contexts.company.models.SearchCompanyResponse;
import br.com.ifsp.tickets.infra.contexts.company.models.UpdateCompanyRequest;
import br.com.ifsp.tickets.infra.contexts.company.presenters.CompanyApiPresenter;
import br.com.ifsp.tickets.infra.contexts.user.persistence.UserJpaEntity;
import br.com.ifsp.tickets.infra.shared.search.AdvancedSearchRequest;
import br.com.ifsp.tickets.infra.shared.search.SearchFilterRequest;
import br.com.ifsp.tickets.infra.shared.search.SortSearchRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CompanyController implements CompanyAPI {

    private final CompanyService companyService;

    @Override
    public ResponseEntity<Void> create(@NotNull CreateCompanyRequest request) {
        final UserJpaEntity authenticatedUser = (UserJpaEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final CreateCompanyInput input = CreateCompanyInput.of(
                authenticatedUser.toAggregate(),
                request.ownerId(),
                request.name(),
                request.description(),
                request.cnpj(),
                request.address().street(),
                request.address().complement(),
                request.address().number(),
                request.address().neighborhood(),
                request.address().city(),
                request.address().state(),
                request.address().zipCode()
        );
        final CreateCompanyOutput output = this.companyService.create(input);

        return ResponseEntity.created(URI.create("/v1/companies/" + output.id())).build();
    }

    @Override
    public ResponseEntity<CompanyResponse> update(String id, UpdateCompanyRequest request) {
        final UserJpaEntity authenticatedUser = (UserJpaEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final UpdateCompanyInput input = UpdateCompanyInput.of(
                id,
                request.name(),
                request.description(),
                request.cnpj(),
                authenticatedUser.toAggregate()
        );
        final UpdateCompanyOutput output = this.companyService.update(input);

        return ResponseEntity.ok(CompanyApiPresenter.present(output));
    }

    @Override
    public ResponseEntity<Pagination<SearchCompanyResponse>> search(Integer page, Integer perPage, AdvancedSearchRequest request) {
        final AdvancedSearchQuery advancedSearchQuery = AdvancedSearchQuery.of(
                page,
                perPage,
                request.sorts().stream().map(SortSearchRequest::toSortSearch).toList(),
                request.filters().stream().map(SearchFilterRequest::toSearchFilter).toList()
        );
        final Pagination<SearchCompanyResponse> pagination = this.companyService.search(advancedSearchQuery).map(CompanyApiPresenter::present);
        return ResponseEntity.ok(pagination);
    }

    @Override
    public ResponseEntity<CompanyResponse> get(String id) {
        final CompanyOutput output = this.companyService.getById(id);
        return ResponseEntity.ok(CompanyApiPresenter.present(output));
    }

    @Override
    public ResponseEntity<Void> delete(String id) {
        final UserJpaEntity authenticatedUser = (UserJpaEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final DeleteCompanyInput input = DeleteCompanyInput.of(id, authenticatedUser.toAggregate());
        this.companyService.delete(input);
        return ResponseEntity.noContent().build();
    }
}
