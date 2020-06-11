package io.github.scarger.placeholders.service.session;

import java.util.concurrent.TimeUnit;

public class Session {

    private static long sessionLifespan = TimeUnit.DAYS.toMillis(2);

    private final String id;
    private final String userId;
    private final long creationTime;

    public Session(String id, String userId) {
        this.id = id;
        this.userId = userId;
        creationTime = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public boolean isExpired() {
        return (System.currentTimeMillis() - creationTime) > sessionLifespan;
    }
}
