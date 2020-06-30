package io.github.scarger.placeholders.model.collection.document;


import com.google.api.client.auth.oauth2.StoredCredential;
import org.bson.types.ObjectId;

public final class GoogleCredentialDocument {

    private ObjectId id;
    private String userId;
    private StoredCredential storedCredential;

    public ObjectId getId() {
        return id;
    }

    public void setId(final ObjectId id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public StoredCredential getStoredCredential() {
        return storedCredential;
    }

    public void setStoredCredential(final StoredCredential storedCredential) {
        this.storedCredential = storedCredential;
    }
}
