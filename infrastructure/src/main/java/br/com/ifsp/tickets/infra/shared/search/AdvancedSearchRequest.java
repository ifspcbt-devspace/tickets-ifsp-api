package br.com.ifsp.tickets.infra.shared.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Advanced Search Request", description = "The advanced search request to be used in search endpoints")
public record AdvancedSearchRequest(
        @JsonProperty("filters")
        @Tag(name = "filters", description = "The filters to be used in the search")
        List<SearchFilterRequest> filters,
        @Tag(name = "sorts", description = "The sorts to be used in the search")
        @JsonProperty("sorts")
        List<SortSearchRequest> sorts
) {
}
