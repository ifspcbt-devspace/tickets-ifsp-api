package br.com.ifsp.tickets.app.auth.recovery.request;

public record RecoveryRequestCommand(
        String login,
        String ipAddress,
        String userAgent
) {

    public RecoveryRequestCommand {
        if (login == null || login.isBlank())
            throw new IllegalArgumentException("'login' is required");
        if (ipAddress == null || ipAddress.isBlank())
            throw new IllegalArgumentException("'ip_address' is required");
        if (userAgent == null || userAgent.isBlank())
            throw new IllegalArgumentException("'user_agent' is required");
    }

    public static RecoveryRequestCommand of(String login, String ipAddress, String userAgent) {
        return new RecoveryRequestCommand(login, ipAddress, userAgent);
    }


}
