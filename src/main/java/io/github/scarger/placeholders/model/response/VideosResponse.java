package io.github.scarger.placeholders.model.response;

public class VideosResponse {

    private String title;
    private String thumbnailUrl;

    public VideosResponse(String title, String thumbnailUrl) {
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
