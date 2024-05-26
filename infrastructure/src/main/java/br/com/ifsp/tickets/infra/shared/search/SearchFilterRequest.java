package br.com.ifsp.tickets.infra.shared.search;

import br.com.ifsp.tickets.domain.shared.search.SearchFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Search Filter Request", description = "The search filter request to be used in search endpoints")
public record SearchFilterRequest(
        @JsonProperty("filter_key")
        @Schema(description = "The key to search for")
        String filterKey,
        @JsonProperty("value")
        @Schema(description = "The value to search for")
        Object value,
        @JsonProperty("operation")
        @Schema(description = "The comparison used to search for the term", allowableValues = {"eq", "ne", "gt", "ge", "lt", "le", "cn", "ic", "bw", "ib", "ew", "ie", "nc", "bn", "en", "nu", "nn"})
        String operation,
        @JsonProperty("data_option")
        @Schema(description = "The option used to combine the search criteria", allowableValues = {"all", "any"})
        String dataOption
) {

    @JsonIgnore
    public SearchFilter toSearchFilter() {
        return SearchFilter.of(filterKey, value, operation, dataOption);
    }
}
