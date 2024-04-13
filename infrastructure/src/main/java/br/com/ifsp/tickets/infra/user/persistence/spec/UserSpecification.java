package br.com.ifsp.tickets.infra.user.persistence.spec;

import br.com.ifsp.tickets.domain.shared.search.SearchFilter;
import br.com.ifsp.tickets.infra.shared.DefaultSpecification;
import br.com.ifsp.tickets.infra.user.persistence.UserJpaEntity;

public class UserSpecification extends DefaultSpecification<UserJpaEntity> {

    public UserSpecification(SearchFilter searchFilter) {
        super(searchFilter);
    }
}
