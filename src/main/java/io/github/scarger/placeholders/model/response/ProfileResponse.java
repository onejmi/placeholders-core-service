package io.github.scarger.placeholders.model.response;

public class ProfileResponse {

    private final String name;
    private final String imageUrl;
    private final double subscriberCount;

    public ProfileResponse(String name, String imageUrl, double subscriberCount) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.subscriberCount = subscriberCount;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public double getSubscriberCount() {
        return subscriberCount;
    }
}
