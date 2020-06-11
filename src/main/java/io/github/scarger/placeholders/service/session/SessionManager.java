package io.github.scarger.placeholders.service.session;

import io.github.scarger.placeholders.CoreService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SessionManager {

    //todo port this to MongoDB or redis

    private final CoreService context;
    private final Map<String, Session> currentSessions;

    public SessionManager(CoreService context) {
        this.context = context;
        currentSessions = new HashMap<>();
    }

    public Map<String, Session> getSessions() {
        return currentSessions;
    }

    public void invalidate(String sessionId) {
        currentSessions.put(sessionId, null);
    }

    public boolean isLoggedIn(String sessionId) {
        try {
            if(!currentSessions.containsKey(sessionId)) {
                return false;
            }

            Session session = currentSessions.get(sessionId);
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
