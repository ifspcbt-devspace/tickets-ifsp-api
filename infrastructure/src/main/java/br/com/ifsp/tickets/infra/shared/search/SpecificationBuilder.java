package br.com.ifsp.tickets.infra.shared.search;

import br.com.ifsp.tickets.domain.shared.search.SearchFilter;
import br.com.ifsp.tickets.domain.shared.search.SearchOption;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SpecificationBuilder<T> {

    private final List<SearchFilter> params;

    public SpecificationBuilder() {
        this.params = new ArrayList<>();
    }

    public final SpecificationBuilder<T> with(String key, String operation, Object value) {
        params.add(SearchFilter.of(key, value, operation, SearchOption.ALL.toString()));
        return this;
    }

    public final SpecificationBuilder<T> with(SearchFilter searchCriteria) {
        params.add(searchCriteria);
        return this;
    }

    public Specification<T> build() {
        if (params.isEmpty()) throw new IllegalArgumentException("No search criteria provided");

        Specification<T> result = new DefaultSpecification<>(params.getFirst());

        for (int idx = 1; idx < params.size(); idx++) {
            final SearchFilter filter = params.get(idx);
            result = SearchOption.getDataOption(filter.dataOption()) == SearchOption.ALL
                    ? Specification.where(result).and(new DefaultSpecification<>(filter))
                    : Specification.where(result).or(new DefaultSpecification<>(filter));
        }
        return result;
    }

}
