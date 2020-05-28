package io.github.scarger.placeholders.model;

public final class RequestError {

    private final int errorCode;
    private final String errorMessage;

    public RequestError(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
