package io.github.scarger.placeholders.model.request;

public class AuthenticationRequest {

    private final String authCode;

    public AuthenticationRequest(String authCode) {
        this.authCode = authCode;
    }

    public String getAuthCode() {
        return authCode;
    }
}
