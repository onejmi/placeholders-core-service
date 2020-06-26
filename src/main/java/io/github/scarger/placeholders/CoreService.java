package io.github.scarger.placeholders;

import io.github.scarger.placeholders.route.AuthenticationRoute;
import io.github.scarger.placeholders.route.LoginStatusRoute;
import io.github.scarger.placeholders.route.api.ProfileRoute;
import io.github.scarger.placeholders.route.api.video.VideoRoute;
import io.github.scarger.placeholders.route.api.video.VideoTitleRoute;
import io.github.scarger.placeholders.service.GoogleAuthService;
import io.github.scarger.placeholders.service.session.SessionManager;
import io.github.scarger.placeholders.util.CoreUtil;
import spark.Response;
import spark.Spark;

import static spark.Spark.*;

public class CoreService {

    private CoreUtil coreUtil;
    private GoogleAuthService authService;
    private SessionManager sessionManager;

    public void start() throws Exception {
        coreUtil = new CoreUtil();
        authService = new GoogleAuthService(this);
        sessionManager = new SessionManager(this);
        registerMiddleware();
        registerRoutes();
        System.out.println("All systems setup!");
        Spark.exception(Exception.class, (exception, request, response) -> {
            exception.printStackTrace();
        });
    }

    private void registerRoutes() {
        post("/authenticate", "application/json", new AuthenticationRoute(this), coreUtil.toJson());
        get("/auth/status", "application/json", new LoginStatusRoute(this), coreUtil.toJson());
        path("/api/v1", () -> {
           before("/*", (req, res) -> {
               if(!sessionManager.isLoggedIn(req.cookie("ph_sid"))) {
                   halt(401, "You're not logged in!");
               }
           });
           path("/profile", () -> {
               get("", new ProfileRoute(this), coreUtil.toJson());
               path("/uploads", () -> {
                   get("", new VideoRoute(this), coreUtil.toJson());
                   path("/update", () -> post("/title", new VideoTitleRoute(this), coreUtil.toJson()));
               });
           });
        });
    }

    private void registerMiddleware() {
        before((req, res) -> disableCors(res));
    }

    private void disableCors(Response res) {
        res.header("Access-Control-Allow-Methods", "GET,POST,PATCH,DELETE,OPTIONS");
        res.header("Access-Control-Allow-Origin", "http://localhost:8080");
        res.header("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
        res.header("Access-Control-Allow-Credentials", "true");
    }

    public CoreUtil util() {
        return coreUtil;
    }

    public GoogleAuthService getAuth() {
        return authService;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

}
