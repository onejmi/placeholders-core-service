package io.github.scarger.placeholders;

import io.github.scarger.placeholders.route.AuthenticationRoute;
import io.github.scarger.placeholders.service.GoogleAuthService;
import io.github.scarger.placeholders.util.CoreUtil;

import static spark.Spark.*;

public class CoreService {

    private CoreUtil coreUtil;
    private GoogleAuthService authService;

    public void start() {
        coreUtil = new CoreUtil();
        authService = new GoogleAuthService();
        registerMiddleware();
        registerRoutes();
        System.out.println("All systems setup!");
    }

    private void registerRoutes() {
        post("/authenticate", "application/json", new AuthenticationRoute(this), coreUtil.toJson());
    }

    private void registerMiddleware() {
        before((req, res) -> res.header("Access-Control-Allow-Origin", "http://localhost:8080"));
    }

    public CoreUtil util() {
        return coreUtil;
    }

    public GoogleAuthService getAuth() {
        return authService;
    }

}
