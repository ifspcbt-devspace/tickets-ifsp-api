package br.com.ifsp.tickets.domain.shared.search;

import java.util.List;

public record AdvancedSearchQuery(
        int page,
        int perPage,
        List<SortSearch> sorts,
        List<SearchFilter> filters
) {

    public AdvancedSearchQuery {
        sorts = sorts == null ? List.of() : sorts;
        page = Math.max(page, 0);
        perPage = Math.max(perPage, 1);
        filters = filters == null ? List.of() : filters;
    }

    public static AdvancedSearchQuery of(int page, int perPage, List<SortSearch> sorts, List<SearchFilter> filters) {
        return new AdvancedSearchQuery(page, perPage, sorts, filters);
    }

}
