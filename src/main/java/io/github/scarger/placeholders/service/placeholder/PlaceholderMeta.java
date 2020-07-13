package io.github.scarger.placeholders.service.placeholder;

public final class PlaceholderMeta {

    private final String placeholder;
    private final String name;
    private final String description;
    private final String imageUrl;

    public PlaceholderMeta(String placeholder, String name, String description, String imageUrl) {
        this.placeholder = placeholder;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
