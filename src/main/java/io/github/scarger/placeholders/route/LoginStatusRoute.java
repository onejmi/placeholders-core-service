package io.github.scarger.placeholders.route;

import io.github.scarger.placeholders.CoreService;
import io.github.scarger.placeholders.model.RequestError;
import io.github.scarger.placeholders.model.response.LoginStatusResponse;
import spark.Request;
import spark.Response;

public class LoginStatusRoute implements spark.Route {

    private final CoreService context;

    public LoginStatusRoute(CoreService context) {
        this.context = context;
    }

    @Override
    public Object handle(Request request, Response response) {
        try {
            return new LoginStatusResponse(context.getAuth().isLoggedIn(
                    request.queryMap("session_id").value()
                )
            );
        } catch (Exception e) {
            return new RequestError(422, "Invalid request format");
        }
    }
}
