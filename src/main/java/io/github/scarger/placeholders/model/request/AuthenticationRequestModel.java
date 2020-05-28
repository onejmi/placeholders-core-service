package io.github.scarger.placeholders.model.request;

public class AuthenticationRequestModel {

    private final String authCode;

    public AuthenticationRequestModel(String authCode) {
        this.authCode = authCode;
    }

    public String getAuthCode() {
        return authCode;
    }
}
