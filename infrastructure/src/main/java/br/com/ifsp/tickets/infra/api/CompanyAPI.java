package br.com.ifsp.tickets.infra.api;

import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.infra.contexts.company.models.CompanyResponse;
import br.com.ifsp.tickets.infra.contexts.company.models.CreateCompanyRequest;
import br.com.ifsp.tickets.infra.contexts.company.models.SearchCompanyResponse;
import br.com.ifsp.tickets.infra.contexts.company.models.UpdateCompanyRequest;
import br.com.ifsp.tickets.infra.shared.APIErrorResponse;
import br.com.ifsp.tickets.infra.shared.search.AdvancedSearchRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/company")
@Tag(name = "Company", description = "Company API - manage companies")
public interface CompanyAPI {

    @PostMapping(consumes = "application/json")
    @Operation(
            summary = "Create company",
            description = "Create a new company",
            security = {
                    @SecurityRequirement(name = "bearer")
            },
            responses = {
                    @ApiResponse(responseCode = "201", description = "Company created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    ResponseEntity<Void> create(@RequestBody CreateCompanyRequest request);

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    @Operation(
            summary = "Update company",
            description = "Update company information",
            security = {
                    @SecurityRequirement(name = "bearer")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Company updated successfully", content = @Content(schema = @Schema(implementation = CompanyResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Company not found", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    ResponseEntity<CompanyResponse> update(@PathVariable String id, @RequestBody UpdateCompanyRequest request);

    @PostMapping(
            value = "/search",
            produces = "application/json")
    @Operation(
            summary = "Search companies",
            description = "Search companies by name, cnpj, state, city, neighborhood, street, number, complement, zip code, phone, email, website, and contact name",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Companies found"),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    ResponseEntity<Pagination<SearchCompanyResponse>> search(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                             @RequestParam(name = "perPage", required = false, defaultValue = "10") Integer perPage,
                                                             @RequestBody AdvancedSearchRequest request);

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(
            summary = "Get company by id",
            description = "Get company information by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Company found", content = @Content(schema = @Schema(implementation = CompanyResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Company not found", content = @Content(schema = @Schema(implementation = APIErrorResponse.class))
                    )
            }
    )
    ResponseEntity<CompanyResponse> get(@PathVariable String id);

    @DeleteMapping(value = "/{id}")
    @Operation(
            summary = "Delete company",
            description = "Delete company by id",
            security = {
                    @SecurityRequirement(name = "bearer")
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Company deleted successfully"),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Company not found", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    ResponseEntity<Void> delete(@PathVariable String id);

}
