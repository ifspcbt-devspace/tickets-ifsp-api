package br.com.ifsp.tickets.infra.api;

import br.com.ifsp.tickets.infra.contexts.zipcode.models.ZipCodeResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/cep")
@Tag(name = "Cep", description = "Cep API - get address by brazilian zip code")
public interface CepAPI {

    @GetMapping(value = "/{cep}", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cep found"),
            @ApiResponse(responseCode = "404", description = "Cep not found")
    })
    ResponseEntity<ZipCodeResponse> get(@PathVariable String cep);
}
