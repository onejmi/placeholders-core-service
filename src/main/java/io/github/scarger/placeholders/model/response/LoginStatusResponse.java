package io.github.scarger.placeholders.model.response;

public class LoginStatusResponse {

    private final boolean loggedIn;

    public LoginStatusResponse(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
}
