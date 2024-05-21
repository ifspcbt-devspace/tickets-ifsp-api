package br.com.ifsp.tickets.infra.api;

import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.infra.contexts.company.models.CompanyResponse;
import br.com.ifsp.tickets.infra.contexts.company.models.CreateCompanyRequest;
import br.com.ifsp.tickets.infra.contexts.company.models.SearchCompanyResponse;
import br.com.ifsp.tickets.infra.contexts.company.models.UpdateCompanyRequest;
import br.com.ifsp.tickets.infra.shared.search.AdvancedSearchRequest;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/company")
@Tag(name = "Company", description = "Company API")
public interface CompanyAPI {

    @PostMapping(consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Company created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    ResponseEntity<Void> create(@RequestBody CreateCompanyRequest request);

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company updated successfully"),
            @ApiResponse(responseCode = "404", description = "Company not found"),
    })
    ResponseEntity<CompanyResponse> update(@PathVariable String id, @RequestBody UpdateCompanyRequest request);

    @GetMapping(
            value = "/search",
            produces = "application/json")
    ResponseEntity<Pagination<SearchCompanyResponse>> search(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                             @RequestParam(name = "perPage", required = false, defaultValue = "10") Integer perPage,
                                                             @RequestBody AdvancedSearchRequest request);

    @GetMapping(value = "/{id}", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company found"),
            @ApiResponse(responseCode = "404", description = "Company not found")
    })
    ResponseEntity<CompanyResponse> get(@PathVariable String id);

    @DeleteMapping(value = "/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Company deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Company not found")
    })
    ResponseEntity<Void> delete(@PathVariable String id);

}
