package io.github.scarger.placeholders.route.api.video;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import io.github.scarger.placeholders.CoreService;
import io.github.scarger.placeholders.model.request.VideoIdentityModel;
import io.github.scarger.placeholders.util.YoutubeUtil;
import spark.Request;
import spark.Response;
import spark.Route;

public class VideoTitleRoute implements Route {

    private CoreService context;

    public VideoTitleRoute(CoreService context) {
        this.context = context;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        VideoIdentityModel vi = context.util().gson().fromJson(request.body(), VideoIdentityModel.class);

        YouTube youtube = YoutubeUtil.getInstanceFromRequest(context, request);
        YouTube.Videos.List req = youtube.videos().list("snippet");
        VideoListResponse res = req.setId(vi.getVideoId()).execute();

        if(!res.isEmpty()) {
            Video video = new Video();
            video.setId(vi.getVideoId());
            VideoSnippet newSnippet = new VideoSnippet();
            newSnippet.setCategoryId(res.getItems().get(0).getSnippet().getCategoryId());
            newSnippet.setTitle(vi.getNewTitle());
            video.setSnippet(newSnippet);

            youtube.videos().update("snippet", video).execute();
            return vi;
        }
        return null;
    }
}
