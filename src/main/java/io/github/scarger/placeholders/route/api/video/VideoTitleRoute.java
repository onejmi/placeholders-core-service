package io.github.scarger.placeholders.route.api.video;

import io.github.scarger.placeholders.CoreService;
import io.github.scarger.placeholders.model.request.VideoIdentityModel;
import io.github.scarger.placeholders.service.session.Session;
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
        Session session = context.getSessionManager().get(request);
        context.getUserManager().getUsers().setTitle(session.getUserId(), vi.getVideoId(), vi.getNewTitle());
        return vi;
    }
}
