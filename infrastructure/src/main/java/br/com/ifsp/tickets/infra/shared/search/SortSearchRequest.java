package br.com.ifsp.tickets.infra.shared.search;

import br.com.ifsp.tickets.domain.shared.search.SortSearch;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Sort Query Request", description = "The sort query request to be used in search endpoints")
public record SortSearchRequest(
        @JsonProperty("sort")
        @Schema(description = "The field to sort the results by")
        String sort,
        @JsonProperty("direction")
        @Schema(description = "The direction to sort the results by", allowableValues = {"asc", "desc"})
        String direction
) {

    @JsonIgnore
    public SortSearch toSortSearch() {
        return SortSearch.of(sort, direction);
    }

}
