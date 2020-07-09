package io.github.scarger.placeholders.service.video;

public class TitleChangeTransaction {

    private final String userId;
    private final String videoId;
    private final String unformattedTitle;

    public TitleChangeTransaction(String userId, String videoId, String unformattedTitle) {
        this.userId = userId;
        this.videoId = videoId;
        this.unformattedTitle = unformattedTitle;
    }

    public String getUserId() {
        return userId;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getUnformattedTitle() {
        return unformattedTitle;
    }
}
