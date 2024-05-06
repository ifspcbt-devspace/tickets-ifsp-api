package br.com.ifsp.tickets.infra.contexts.user.persistence.spec;

import br.com.ifsp.tickets.domain.shared.search.SearchFilter;
import br.com.ifsp.tickets.domain.shared.search.SearchOperation;
import br.com.ifsp.tickets.infra.contexts.user.persistence.UserJpaEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecificationBuilder {

    private final List<SearchFilter> params;

    public UserSpecificationBuilder() {
        this.params = new ArrayList<>();
    }

    public final UserSpecificationBuilder with(String key, String operation, Object value) {
        params.add(SearchFilter.of(key, value, operation, SearchOperation.ALL.toString()));
        return this;
    }

    public final UserSpecificationBuilder with(SearchFilter searchCriteria) {
        params.add(searchCriteria);
        return this;
    }

    public Specification<UserJpaEntity> build() {
        if (params.isEmpty()) {
            return null;
        }

        Specification<UserJpaEntity> result = new UserSpecification(params.getFirst());

        for (int idx = 1; idx < params.size(); idx++) {
            final SearchFilter filter = params.get(idx);
            result = SearchOperation.getDataOption(filter.dataOption()) == SearchOperation.ALL
                    ? Specification.where(result).and(new UserSpecification(filter))
                    : Specification.where(result).or(new UserSpecification(filter));
        }
        return result;
    }

}
