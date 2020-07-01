package io.github.scarger.placeholders.service.session;

import org.bson.types.ObjectId;

import java.util.concurrent.TimeUnit;

public class Session {

    //ignored during serialization
    private static final long sessionLifespan = TimeUnit.DAYS.toMillis(2);

    private ObjectId id;
    private String userId;
    private String sessionId;
    private long creationTime;

    //to support serialization
    public Session() {}

    public Session(String sessionId, String userId) {
        this.sessionId = sessionId;
        this.userId = userId;
        creationTime = System.currentTimeMillis();
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public boolean isExpired() {
        return (System.currentTimeMillis() - creationTime) > sessionLifespan;
    }
}
