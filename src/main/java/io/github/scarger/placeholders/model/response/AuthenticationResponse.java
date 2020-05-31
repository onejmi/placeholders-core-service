package io.github.scarger.placeholders.model.response;

public class AuthenticationResponse {

    private final String sessionId;

    public AuthenticationResponse(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }
}
