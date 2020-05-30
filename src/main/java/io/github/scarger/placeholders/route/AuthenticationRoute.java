package io.github.scarger.placeholders.route;

import com.google.gson.JsonSyntaxException;
import io.github.scarger.placeholders.CoreService;
import io.github.scarger.placeholders.model.RequestError;
import io.github.scarger.placeholders.model.request.AuthenticationRequest;
import io.github.scarger.placeholders.model.response.AuthenticationResponse;
import spark.Request;
import spark.Response;

public class AuthenticationRoute implements spark.Route {

    private final CoreService context;

    public AuthenticationRoute(CoreService context) {
        this.context = context;
    }

    @Override
    public Object handle(Request request, Response response) {
        try {
            AuthenticationRequest authReq =
                    context.util().gson().fromJson(request.body(), AuthenticationRequest.class);
            return new AuthenticationResponse(context.getAuth().authenticate(authReq).getSubject());
        } catch (JsonSyntaxException e) {
            return new RequestError(422, "Invalid request format.");
        }
    }
}
