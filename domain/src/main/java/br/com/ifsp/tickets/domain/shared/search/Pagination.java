package br.com.ifsp.tickets.domain.shared.search;

import java.util.List;
import java.util.function.Function;

public record Pagination<T>(
        int currentPage,
        int perPage,
        long total,
        List<T> items
) {

    public static <T> Pagination<T> of(int currentPage, int perPage, long total, List<T> items) {
        return new Pagination<>(currentPage, perPage, total, items);
    }

    public <R> Pagination<R> map(final Function<T, R> mapper) {
        final List<R> aNewList = this.items.stream()
                .map(mapper)
                .toList();

        return new Pagination<>(currentPage(), perPage(), total(), aNewList);
    }
}
