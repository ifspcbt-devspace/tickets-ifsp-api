package br.com.ifsp.tickets.infra.contexts.user.persistence.spec;

import br.com.ifsp.tickets.domain.shared.search.SearchFilter;
import br.com.ifsp.tickets.infra.contexts.user.persistence.UserJpaEntity;
import br.com.ifsp.tickets.infra.shared.DefaultSpecification;

public class UserSpecification extends DefaultSpecification<UserJpaEntity> {

    public UserSpecification(SearchFilter searchFilter) {
        super(searchFilter);
    }
}
