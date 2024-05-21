package br.com.ifsp.tickets.infra.shared.search;

import br.com.ifsp.tickets.domain.shared.search.SearchFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Search Filter Request", description = "The search filter request to be used in search endpoints")
public record SearchFilterRequest(
        @JsonProperty("filter_key")
        @Tag(name = "Filter Key", description = "The key to search for")
        String filterKey,
        @JsonProperty("value")
        @Tag(name = "Value", description = "The value to search for")
        Object value,
        @JsonProperty("operation")
        @Tag(name = "Search Operation", description = "The comparison used to search for the term", extensions = {
                @Extension(properties = {
                        @ExtensionProperty(name = "EQUALS", value = "eq"),
                        @ExtensionProperty(name = "NOT_EQUALS", value = "ne"),
                        @ExtensionProperty(name = "GREATER_THAN", value = "gt"),
                        @ExtensionProperty(name = "GREATER_THAN_EQUAL", value = "ge"),
                        @ExtensionProperty(name = "LESS_THAN", value = "lt"),
                        @ExtensionProperty(name = "LESS_THAN_EQUAL", value = "le"),
                        @ExtensionProperty(name = "CONTAINS", value = "cn"),
                        @ExtensionProperty(name = "INSENSITIVE_CONTAINS", value = "ic"),
                        @ExtensionProperty(name = "BEGINS_WITH", value = "bw"),
                        @ExtensionProperty(name = "INSENSITIVE_BEGINS_WITH", value = "ib"),
                        @ExtensionProperty(name = "ENDS_WITH", value = "ew"),
                        @ExtensionProperty(name = "INSENSITIVE_ENDS_WITH", value = "ie"),
                        @ExtensionProperty(name = "DOES_NOT_CONTAIN", value = "nc"),
                        @ExtensionProperty(name = "DOES_NOT_BEGIN_WITH", value = "bn"),
                        @ExtensionProperty(name = "DOES_NOT_END_WITH", value = "en"),
                        @ExtensionProperty(name = "IS_NULL", value = "nu"),
                        @ExtensionProperty(name = "IS_NOT_NULL", value = "nn")
                }
                )})
        String operation,
        @JsonProperty("data_option")
        @Tag(name = "Data Option", description = "The option used to combine the search criteria", extensions = {
                @Extension(properties = {
                        @ExtensionProperty(name = "ALL", value = "all"),
                        @ExtensionProperty(name = "ANY", value = "any")
                })
        })
        String dataOption
) {

    @JsonIgnore
    public SearchFilter toSearchFilter() {
        return SearchFilter.of(filterKey, value, operation, dataOption);
    }
}
