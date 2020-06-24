package io.github.scarger.placeholders.route.api;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import io.github.scarger.placeholders.CoreService;
import io.github.scarger.placeholders.model.response.VideosResponse;
import io.github.scarger.placeholders.util.YoutubeUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.stream.Collectors;

public class VideoRoute implements Route {

    private CoreService context;

    public VideoRoute(CoreService context) {
        this.context = context;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        YouTube youtube = YoutubeUtil.getInstanceFromRequest(context, request);
        Channel channel = YoutubeUtil.getOwnChannel(youtube, "contentDetails");
        if(channel != null) {
            String uploadsId = channel.getContentDetails().getRelatedPlaylists().getUploads();
            YouTube.PlaylistItems.List req = youtube.playlistItems().list("snippet,contentDetails,status");
            PlaylistItemListResponse res = req.setPlaylistId(uploadsId).execute();

            return res.getItems()
                    .stream()
                    .map(PlaylistItem::getSnippet)
                    .map(snippet -> new VideosResponse(snippet.getTitle(), snippet.getThumbnails().getStandard().getUrl()))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
