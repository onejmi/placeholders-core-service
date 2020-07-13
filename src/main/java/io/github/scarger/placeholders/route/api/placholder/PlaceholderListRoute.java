package io.github.scarger.placeholders.route.api.placholder;

import io.github.scarger.placeholders.CoreService;
import io.github.scarger.placeholders.service.session.Session;
import spark.Request;
import spark.Response;
import spark.Route;

public class PlaceholderListRoute implements Route {

    private final CoreService context;

    public PlaceholderListRoute(CoreService context) {
        this.context = context;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Session session = context.getSessionManager().get(request);
        return context.getUserManager().getUsers().getUser(session.getUserId()).getCurrentPlaceholders();
    }
}
