package io.github.scarger.placeholders.util;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import io.github.scarger.placeholders.CoreService;
import io.github.scarger.placeholders.service.session.Session;
import spark.Request;

import java.io.IOException;

public class YoutubeUtil {
    public static YouTube getInstanceFromRequest(CoreService context, Request request) {
        Session session = context.getSessionManager().get(request.cookie("ph_sid"));
        Credential credential = context.getAuth().getCredential(session.getUserId());
        return context.getAuth().getYoutubeService(credential);
    }

    public static Channel getOwnChannel(YouTube youtube, String parts) {
        try {
            YouTube.Channels.List req = youtube.channels().list(parts);
            ChannelListResponse res = req.setMine(true).execute();
            return res.getItems().size() > 0 ? res.getItems().get(0) : null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
