package io.github.scarger.placeholders.model.response;

public class AuthenticationResponse {

    private final String subject;

    public AuthenticationResponse(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }
}
