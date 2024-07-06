package br.com.ifsp.tickets.infra.shared.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Advanced Search Request", description = "The advanced search request to be used in search endpoints")
public record AdvancedSearchRequest(
        @JsonProperty(value = "filters", defaultValue = "[]")
        @Schema(description = "The filters to be used in the search")
        List<SearchFilterRequest> filters,
        @JsonProperty(value = "sorts", defaultValue = "[]")
        @Schema(description = "The sorts to be used in the search")
        List<SortSearchRequest> sorts
) {
}
