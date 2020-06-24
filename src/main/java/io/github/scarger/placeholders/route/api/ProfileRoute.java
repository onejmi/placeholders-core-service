package io.github.scarger.placeholders.route.api;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import io.github.scarger.placeholders.CoreService;
import io.github.scarger.placeholders.model.response.ProfileResponse;
import io.github.scarger.placeholders.util.YoutubeUtil;
import spark.Request;
import spark.Response;

import java.io.IOException;


public class ProfileRoute implements spark.Route {

    private final CoreService context;

    public ProfileRoute(CoreService context) {
        this.context = context;
    }

    @Override
    public Object handle(Request request, Response response) {
        YouTube youtube = YoutubeUtil.getInstanceFromRequest(context, request);
        Channel channel = YoutubeUtil.getOwnChannel(youtube, "snippet,contentDetails,statistics");
        if(channel != null) {
            String name = channel.getSnippet().getTitle();
            String image = channel.getSnippet().getThumbnails().getDefault().getUrl();
            int subCount = channel.getStatistics().getSubscriberCount().intValue();
            return new ProfileResponse(name, image, subCount);
        }
        return null;
    }
}
