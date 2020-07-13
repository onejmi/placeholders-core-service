package io.github.scarger.placeholders.route.api.placholder;

import io.github.scarger.placeholders.CoreService;
import io.github.scarger.placeholders.model.request.PlaceholderStateChangeModel;
import io.github.scarger.placeholders.service.session.Session;
import spark.Request;
import spark.Response;
import spark.Route;

public class PlaceholderChangeRoute implements Route {

    private final CoreService context;

    public PlaceholderChangeRoute(CoreService context) {
        this.context = context;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        // { "placeholder_name" : "abc", "enabled" : true/false}
        Session session = context.getSessionManager().get(request);
        PlaceholderStateChangeModel placeholderChange =
                context.util().gson().fromJson(request.body(), PlaceholderStateChangeModel.class);
        context.getPlaceholderActivator()
                .setState(session.getUserId(), placeholderChange.getPlaceholderName(), placeholderChange.isEnabled());
        return placeholderChange;
    }
}
