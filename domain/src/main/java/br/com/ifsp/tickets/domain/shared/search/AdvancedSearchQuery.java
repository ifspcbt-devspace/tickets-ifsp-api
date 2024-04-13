package br.com.ifsp.tickets.domain.shared.search;

import java.util.List;

public record AdvancedSearchQuery(
        int page,
        int perPage,
        String sort,
        String direction,
        List<SearchFilter> filters
) {

    public AdvancedSearchQuery {
        direction = direction == null ? "asc" : direction;
        sort = sort == null ? "id" : sort;
        page = Math.max(page, 0);
        perPage = Math.max(perPage, 1);
        filters = filters == null ? List.of() : filters;
    }

    public static AdvancedSearchQuery of(int page, int perPage, String sort, String direction, List<SearchFilter> filters) {
        return new AdvancedSearchQuery(page, perPage, sort, direction, filters);
    }

}
