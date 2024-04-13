package br.com.ifsp.tickets.domain.shared.search;

public record SearchFilter(
        String filterKey,
        Object value,
        String operation,
        String dataOption
) {

    public static SearchFilter of(String filterKey, Object value, String operation, String dataOption) {
        return new SearchFilter(filterKey, value, operation, dataOption);
    }

}
