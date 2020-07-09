package io.github.scarger.placeholders.service.video;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.api.services.youtube.model.VideoSnippet;
import io.github.scarger.placeholders.CoreService;
import io.github.scarger.placeholders.service.data.Disposable;
import io.github.scarger.placeholders.util.transformer.TitleTransformer;

import java.io.IOException;

public class VideoTitleManager implements Disposable {

    private final CoreService context;
    private final TitleTransformer titleTransformer;
    private final TitleChangeScheduler changeScheduler;

    public VideoTitleManager(CoreService context) {
        this.context = context;
        titleTransformer = new TitleTransformer(context);
        changeScheduler = new TitleChangeScheduler(this, context);

        //begin loop
        changeScheduler.beginQueuer();
        changeScheduler.beginWorker();
    }

    public void updateTitle(String userId, String videoId, String formattedTitle) {
        try {
            Credential credential = context.getAuth().getCredential(userId);
            YouTube youtube = context.getAuth().getYoutubeService(credential);
            YouTube.Videos.List req = youtube.videos().list("snippet");
            VideoListResponse res = req.setId(videoId).execute();

            if(!res.isEmpty()) {
                VideoSnippet oldSnippet = res.getItems().get(0).getSnippet();
                if(!oldSnippet.getTitle().equals(formattedTitle)) {
                    Video video = new Video();
                    video.setId(videoId);
                    VideoSnippet newSnippet = new VideoSnippet();
                    newSnippet.setCategoryId(oldSnippet.getCategoryId());
                    newSnippet.setTitle(formattedTitle);
                    video.setSnippet(newSnippet);

                    youtube.videos().update("snippet", video).execute();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TitleTransformer getTitleTransformer() {
        return titleTransformer;
    }

    @Override
    public void dispose() {
        changeScheduler.dispose();
    }
}
