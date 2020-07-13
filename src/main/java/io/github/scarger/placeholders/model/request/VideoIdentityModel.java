package io.github.scarger.placeholders.model.request;

public class VideoIdentityModel {

    private final String videoId;
    private final String newTitle;

    public VideoIdentityModel(String videoId, String newTitle) {
        this.videoId = videoId;
        this.newTitle = newTitle;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getNewTitle() {
        return newTitle;
    }

}
