package io.github.scarger.placeholders;

import io.github.scarger.placeholders.route.AuthenticationRoute;
import io.github.scarger.placeholders.route.LoginStatusRoute;
import io.github.scarger.placeholders.route.api.ProfileRoute;
import io.github.scarger.placeholders.route.api.video.VideoRoute;
import io.github.scarger.placeholders.route.api.video.VideoTitleRoute;
import io.github.scarger.placeholders.service.GoogleAuthService;
import io.github.scarger.placeholders.service.data.DatabaseManager;
import io.github.scarger.placeholders.service.data.Disposable;
import io.github.scarger.placeholders.service.data.user.UserManager;
import io.github.scarger.placeholders.service.session.SessionManager;
import io.github.scarger.placeholders.service.video.VideoTitleManager;
import io.github.scarger.placeholders.util.CoreUtil;
import spark.Response;
import spark.Spark;

import static spark.Spark.*;

public class CoreService implements Disposable {

    private CoreUtil coreUtil;
    private GoogleAuthService authService;
    private SessionManager sessionManager;
    private DatabaseManager databaseManager;
    private UserManager userManager;
    private VideoTitleManager videoTitleManager;

    public void start() throws Exception {
        coreUtil = new CoreUtil();

        databaseManager = new DatabaseManager(this);
        databaseManager.init();

        userManager = new UserManager(this);
        authService = new GoogleAuthService(this);
        sessionManager = new SessionManager(this);
        videoTitleManager = new VideoTitleManager(this);
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
               if(!req.requestMethod().equalsIgnoreCase("OPTIONS") && !sessionManager.isLoggedIn(req.cookie("ph_sid"))) {
                   halt(401, "You're not logged in!");
               }
           });
           options("/*", (req, res) -> "OK");
           path("/profile", () -> {
               get("", new ProfileRoute(this), coreUtil.toJson());
               path("/uploads", () -> {
                   get("", new VideoRoute(this), coreUtil.toJson());
                   path("/update", () -> patch("/title", new VideoTitleRoute(this), coreUtil.toJson()));
               });
           });
        });
    }

    private void registerMiddleware() {
        before((req, res) -> configureCors(res));
    }

    private void configureCors(Response res) {
        res.header("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE, OPTIONS");
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

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public VideoTitleManager getVideoTitleManager() {
        return videoTitleManager;
    }

    @Override
    public void dispose() {
        videoTitleManager.dispose();
    }
}
