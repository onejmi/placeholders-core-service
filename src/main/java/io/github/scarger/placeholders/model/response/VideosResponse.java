package io.github.scarger.placeholders.model.response;

public class VideosResponse {

    private String title;
    private String thumbnailUrl;
    private String id;

    public VideosResponse(String title, String thumbnailUrl, String videoId) {
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.id = videoId;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getId() {
        return id;
    }
}
