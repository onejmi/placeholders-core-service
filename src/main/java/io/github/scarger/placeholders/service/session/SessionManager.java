package io.github.scarger.placeholders.service.session;

import io.github.scarger.placeholders.CoreService;
import io.github.scarger.placeholders.model.collection.UserSessionCollection;
import spark.Request;

import java.io.IOException;

public class SessionManager {

    private final CoreService context;
    private final UserSessionCollection sessionCollection;

    public SessionManager(CoreService context) {
        this.context = context;
        sessionCollection = new UserSessionCollection(context.getDatabaseManager().getDB());
    }

    public void add(Session session) {
        sessionCollection.add(session);
    }

    public Session get(String sessionId) {
        return sessionCollection.get(sessionId);
    }

    public Session get(Request request) {
        return context.getSessionManager().get(request.cookie("ph_sid"));
    }

    public void invalidate(String sessionId) {
        sessionCollection.remove(sessionId);
    }

    public boolean isLoggedIn(String sessionId) {
        try {
            Session session = get(sessionId);
            if(session == null) {
                return false;
            }

            if(session.isExpired()) {
                invalidate(sessionId);
                return false;
            }

            return context.getAuth().getCredentials().containsKey(session.getUserId());
        } catch (IOException e) {
            return false;
        }
    }

}
