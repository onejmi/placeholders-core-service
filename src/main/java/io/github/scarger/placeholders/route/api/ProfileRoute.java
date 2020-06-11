package io.github.scarger.placeholders.route.api;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import io.github.scarger.placeholders.CoreService;
import io.github.scarger.placeholders.model.response.ProfileResponse;
import io.github.scarger.placeholders.service.session.Session;
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
        Session session = context.getSessionManager().getSessions().get(request.cookie("ph_sid"));
        Credential credential = context.getAuth().getCredential(session.getUserId());
        YouTube youtube = context.getAuth().getYoutubeService(credential);
        try {
            YouTube.Channels.List req = youtube.channels().list("snippet,contentDetails,statistics");
            ChannelListResponse res = req.setMine(true).execute();
            if(res.getItems().size() > 0) {
                Channel channel = res.getItems().get(0);
                String name = channel.getSnippet().getTitle();
                String image = channel.getSnippet().getThumbnails().getDefault().getUrl();
                int subCount = channel.getStatistics().getSubscriberCount().intValue();
                return new ProfileResponse(name, image, subCount);
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
