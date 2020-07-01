package io.github.scarger.placeholders.model.collection;

import com.mongodb.client.MongoDatabase;
import io.github.scarger.placeholders.service.session.Session;

import static com.mongodb.client.model.Filters.eq;

public class UserSessionCollection extends DataCollection<Session> {
    public UserSessionCollection(MongoDatabase database) {
        super(database, "sessions", Session.class);
    }

    public Session get(String sessionId) {
        return collection.find(eq("sessionId", sessionId)).first();
    }

    public void add(Session session) {
        collection.insertOne(session);
    }

    public void remove(String sessionId) {
        collection.deleteOne(eq("sessionId", sessionId));
    }
}
