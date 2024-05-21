package br.com.ifsp.tickets.domain.shared.search;

public record SortSearch(
        String sort,
        String direction
) {

    public SortSearch {
        direction = direction == null ? "asc" : direction;
        sort = sort == null ? "id" : sort;
    }

    public static SortSearch of(String sort, String direction) {
        return new SortSearch(sort, direction);
    }
}
