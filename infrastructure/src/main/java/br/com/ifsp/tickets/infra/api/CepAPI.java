package br.com.ifsp.tickets.infra.api;

import br.com.ifsp.tickets.infra.contexts.zipcode.models.ZipCodeResponse;
import br.com.ifsp.tickets.infra.shared.APIErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/cep")
@Tag(name = "Cep", description = "Cep API - get address by brazilian zip code")
public interface CepAPI {

    @GetMapping(value = "/{cep}", produces = "application/json")
    @Operation(
            summary = "Get address by cep",
            description = "Get address information by brazilian zip code",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cep found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ZipCodeResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Cep not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    ResponseEntity<ZipCodeResponse> get(@PathVariable String cep);
}
