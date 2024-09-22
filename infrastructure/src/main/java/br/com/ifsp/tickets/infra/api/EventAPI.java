package br.com.ifsp.tickets.infra.api;

import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.infra.api.controllers.ExceptionController;
import br.com.ifsp.tickets.infra.contexts.event.core.models.CreateEventRequest;
import br.com.ifsp.tickets.infra.contexts.event.core.models.EventResponse;
import br.com.ifsp.tickets.infra.contexts.event.core.models.SearchEventResponse;
import br.com.ifsp.tickets.infra.shared.search.AdvancedSearchRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/v1/event")
@Tag(name = "Event", description = "Event API - manage events from a company")
public interface EventAPI {

    @PostMapping(consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    ResponseEntity<Void> create(@RequestBody CreateEventRequest request);

    @PostMapping(
            value = "/search",
            produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Events list"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    ResponseEntity<Pagination<SearchEventResponse>> search(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                           @RequestParam(name = "perPage", required = false, defaultValue = "10") Integer perPage,
                                                           @RequestBody AdvancedSearchRequest request);

    @GetMapping(value = "/{id}", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event found"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    ResponseEntity<EventResponse> get(@PathVariable String id);

    @GetMapping(
            value = "/{id}/thumbnail"
    )
    @Operation(
            operationId = "getThumbnail",
            summary = "Obter thumbnail",
            description = "Obter thumbnail do evento.",
            parameters = @Parameter(name = "id", description = "ID do evento a ser consultado", in = ParameterIn.PATH, example = "c971869b-2dd3-4934-beac-172a9a229735", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Thumbnail found", content = @Content(mediaType = "image/png", schema = @Schema(type = "string", format = "binary"))),
                    @ApiResponse(responseCode = "400", description = "Invalid id", content = @Content(schema = @Schema(implementation = ExceptionController.CustomErrorResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ExceptionController.CustomErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Thumbnail not found", content = @Content(schema = @Schema(implementation = ExceptionController.CustomErrorResponse.class)))
            },
            method = "GET"
    )
    ResponseEntity<byte[]> getThumbnail(@PathVariable String id);

    @DeleteMapping(
            value = "/{id}/thumbnail"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Thumbnail deleted"),
                    @ApiResponse(responseCode = "400", description = "Invalid id"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Thumbnail not found")
            }
    )
    @Operation(
            operationId = "deleteThumbnail",
            summary = "Deletar thumbnail",
            description = "Deletar thumbnail do evento.",
            parameters = @Parameter(name = "id", description = "ID do evento a ser consultado", in = ParameterIn.PATH, example = "c971869b-2dd3-4934-beac-172a9a229735", required = true),
            method = "DELETE",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Thumbnail deleted"),
                    @ApiResponse(responseCode = "400", description = "Invalid id", content = @Content(schema = @Schema(implementation = ExceptionController.CustomErrorResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ExceptionController.CustomErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Thumbnail not found", content = @Content(schema = @Schema(implementation = ExceptionController.CustomErrorResponse.class)))
            }
    )
    ResponseEntity<?> deleteThumbnail(@PathVariable String id);

    @PostMapping(
            value = "/{id}/thumbnail",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(
            operationId = "uploadThumbnail",
            summary = "Upload thumbnail",
            description = "Upload thumbnail do evento.",
            parameters = {
                    @Parameter(name = "id", description = "ID do evento a ser consultado", in = ParameterIn.PATH, example = "c971869b-2dd3-4934-beac-172a9a229735", required = true),
                    @Parameter(name = "file", description = "Arquivo de imagem", in = ParameterIn.QUERY, required = true, content = @Content(mediaType = "multipart/form-data", schema = @Schema(type = "string", format = "binary"))),
                    @Parameter(name = "fileName", description = "Nome do arquivo", in = ParameterIn.QUERY, required = true, example = "thumbnail")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Thumbnail uploaded"),
                    @ApiResponse(responseCode = "400", description = "Invalid id", content = @Content(schema = @Schema(implementation = ExceptionController.CustomErrorResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ExceptionController.CustomErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Event not found", content = @Content(schema = @Schema(implementation = ExceptionController.CustomErrorResponse.class)))
            },
            method = "POST"
    )
    ResponseEntity<?> uploadThumbnail(@PathVariable String id, @RequestParam("file") MultipartFile file, @RequestParam("fileName") String fileName);
}
