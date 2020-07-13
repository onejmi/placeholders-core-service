package io.github.scarger.placeholders.model.request;

public class PlaceholderStateChangeModel {
    private final String placeholderName;
    private final boolean enabled;

    public PlaceholderStateChangeModel(String placeholderName, boolean enabled) {
        this.placeholderName = placeholderName;
        this.enabled = enabled;
    }

    public String getPlaceholderName() {
        return placeholderName;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
