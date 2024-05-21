package br.com.ifsp.tickets.domain.shared.search;

public enum SearchOperation {

    CONTAINS, INSENSITIVE_CONTAINS, DOES_NOT_CONTAIN, EQUAL, NOT_EQUAL, BEGINS_WITH,
    INSENSITIVE_BEGINS_WITH, DOES_NOT_BEGIN_WITH, INSENSITIVE_ENDS_WITH, ENDS_WITH, DOES_NOT_END_WITH,
    NUL, NOT_NULL, GREATER_THAN, GREATER_THAN_EQUAL, LESS_THAN,
    LESS_THAN_EQUAL;

    public static SearchOperation getSimpleOperation(final String input) {
        return switch (input) {
            case "cn" -> CONTAINS;
            case "ic" -> INSENSITIVE_CONTAINS;
            case "nc" -> DOES_NOT_CONTAIN;
            case "eq" -> EQUAL;
            case "ne" -> NOT_EQUAL;
            case "bw" -> BEGINS_WITH;
            case "ib" -> INSENSITIVE_BEGINS_WITH;
            case "bn" -> DOES_NOT_BEGIN_WITH;
            case "ie" -> INSENSITIVE_ENDS_WITH;
            case "ew" -> ENDS_WITH;
            case "en" -> DOES_NOT_END_WITH;
            case "nu" -> NUL;
            case "nn" -> NOT_NULL;
            case "gt" -> GREATER_THAN;
            case "ge" -> GREATER_THAN_EQUAL;
            case "lt" -> LESS_THAN;
            case "le" -> LESS_THAN_EQUAL;
            default -> null;
        };
    }

}
