package br.com.ifsp.tickets.domain.shared.search;

public enum SearchOption {

    ANY, ALL;

    public static SearchOption getDataOption(final String dataOption) {
        return switch (dataOption) {
            case "all" -> ALL;
            case "any" -> ANY;
            default -> null;
        };
    }
}
