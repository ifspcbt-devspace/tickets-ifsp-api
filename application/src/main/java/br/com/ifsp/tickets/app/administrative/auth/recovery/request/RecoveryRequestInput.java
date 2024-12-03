package br.com.ifsp.tickets.app.administrative.auth.recovery.request;

public record RecoveryRequestInput(
        String login,
        String ipAddress,
        String userAgent
) {

    public RecoveryRequestInput {
        if (login == null || login.isBlank())
            throw new IllegalArgumentException("'login' is required");
        if (ipAddress == null || ipAddress.isBlank())
            throw new IllegalArgumentException("'ip_address' is required");
        if (userAgent == null || userAgent.isBlank())
            throw new IllegalArgumentException("'user_agent' is required");
    }

    public static RecoveryRequestInput of(String login, String ipAddress, String userAgent) {
        return new RecoveryRequestInput(login, ipAddress, userAgent);
    }


}
