package br.com.ifsp.tickets.infra.shared.utils;

import org.springframework.data.jpa.domain.Specification;

public final class SpecificationUtils {

    private SpecificationUtils() {
    }

    public static <T> Specification<T> like(final String prop, final String term) {
        return (root, query, cb) -> cb.like(cb.upper(root.get(prop).as(String.class)), like(term.toUpperCase()));
    }

    public static <T> Specification<T> equal(final String prop, final String term) {
        return (root, query, cb) -> cb.equal(cb.upper(root.get(prop).as(String.class)), term.toUpperCase());
    }

    public static <T> Specification<T> like(final String prop, final String term, final String joinProp) {
        return (root, query, cb) -> {
            var table = root.join(prop);
            return cb.like(cb.upper(table.get(joinProp).as(String.class)), like(term.toUpperCase()));
        };
    }

    private static String like(final String term) {
        if (term == null) return null;
        return "%" + term + "%";
    }

}